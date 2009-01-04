<?
define("MAX_WIDTH","maxWidth");
define("MAX_HEIGHT","maxHeight");
define("THUMB_WIDTH","thumbWidth");
define("THUMB_HEIGHT","thumbHeight");
define("THUMB_PREFIX","thumbPrefix");
define("THUMB_FILTER","thumbFilter");
define("THUMBS","thumbs");
define('FORCE_SCALE',"forceScale");
define('ASPECT_RATIO',"aspectRatio");

class ImageUpload extends Upload{
	/**
	 *
	 */
	function ImageUpload($fieldName, $fieldValue, $thumbs = 0, $sizes = NULL){
		parent::Upload($fieldName, $fieldValue);
		
		$this->opts[MAX_WIDTH]=DEF_MAX_IMG_WIDTH;
		$this->opts[MAX_HEIGHT]=DEF_MAX_IMG_HEIGHT;
		$this->opts[FORCE_SCALE]=DEF_FORCE_SCALE;
		$this->opts[ASPECT_RATIO]=DEF_ASPECT_RATIO;
		$this->opts[THUMBS]=$thumbs;
		for($i=0;$i<$thumbs;$i++){ //configure the size of each thumbnail
			$this->opts[THUMB_WIDTH.$i] =  $sizes[$i][0]; //W
			$this->opts[THUMB_HEIGHT.$i] = $sizes[$i][1]; //H
			$this->opts[THUMB_PREFIX.$i] = $sizes[$i][2]; //preffix
			$this->opts[THUMB_FILTER.$i] = $sizes[$i][3]; //filter
		}
	}
	
	function uploadFile($ARRAY){
		$error = parent::uploadFile($ARRAY);
		if($error->code == 0){
			$src = $error->description;
			if($src != ''){
				if($this->opts[FORCE_SCALE])
					$error=$this->scaleImage($src,$this->opts[MAX_WIDTH],$this->opts[MAX_HEIGHT],$this->opts[ASPECT_RATIO]);
				
				//generar thumbnails?
				if($this->opts[THUMBS]!=0){
					for($i=0,$n=$this->opts[THUMBS];$i<$n;$i++){
						$error1=$this->scaleImage($src,
													$this->opts[THUMB_WIDTH.$i],
													$this->opts[THUMB_HEIGHT.$i],
													$this->opts[ASPECT_RATIO],
													$this->opts[THUMB_FILTER.$i],
													str_replace($this->opts[PREFIX],$this->opts[THUMB_PREFIX.$i],$src)
												  );
					}
				}
			}
		}
		return $error;
	}
	
	function scaleImage($fileRel,$width,$height,$ratio,$filter='',$target=''){
		$file = ROOT.'/'.$fileRel;
		error_reporting(0);
		$type=".jpg";
		$src=imagecreatefromjpeg($file);
		
		if(($src=="" || $src==NULL)&& function_exists("imagecreatefromgif")){
			$type=".gif";
			$src=imagecreatefromgif($file);
		}
		
		if(($src=="" || $src==NULL)&& function_exists("imagecreatefrompng")){
			$type=".png";
			$src=imagecreatefrompng($file);
		}
		
		if(($src=="" || $src==NULL)&& function_exists("imagecreatefromwbmp")){
			$type=".bmp";
			$src=imagecreatefromwbmp($file);
		}
		
		if($src=="" || $src==NULL) 
			return new Error(-4,MSG_UPLOAD_UNSUPPORTED_FORMAT_READ);
		
		$oriW=imagesx($src);
		$oriH=imagesy($src);
		
		if($ratio){
			if($oriW < $width && $oriH < $height){
				$widthResized=$oriW;
				$heightResized=$oriH;
			}else{
				if($oriW > $oriH){
					$widthResized=$width;
					$heightResized=number_format(($oriH*$width)/$oriW,0);
				}else{
					$heightResized=$height;
					$widthResized=number_format(($oriW*$height)/$oriH,0);
				}
			}
		}else{
			$widthResized=$width;
			$heightResized=$height;
		}
		
		$resize = imagecreatetruecolor($widthResized, $heightResized);
		imagecopyresampled($resize, $src, 0, 0, 0, 0, $widthResized, $heightResized, $oriW, $oriH);
		
		$youcan=false;
		
		if($filter!='')
			eval("\$this->".$filter."(\$resize);");
		
		if($target == '') $target = $fileRel;
		
		if($type==".jpg" && function_exists("imagejpeg")){
			imagejpeg($resize,ROOT.'/'.$target,100); 
			$youcan=true;
		}else if($type==".gif" && function_exists("imagegif")){
			imagegif($resize,ROOT.'/'.$target); 
			$youcan=true;
		}else if($type==".png"  && function_exists("imagepng")){
			imagepng($resize,ROOT.'/'.$target);
			$youcan=true;
		}else if($type==".bmp"  && function_exists("imagewbmp")){
			imagewbmp($resize,ROOT.'/'.$target);
			$youcan=true;
		}
		
		ImageDestroy($src); 
		ImageDestroy($resize); 
		if($youcan) return new Error(0,$target);
		return new Error(-4,MSG_UPLOAD_UNSUPPORTED_FORMAT_WRITE);
	}
	
	function image2grayscale(&$im){
		$imgw = imagesx($im);
		$imgh = imagesy($im);

		for($i=0; $i<$imgw; $i++){
			for($j=0; $j<$imgh; $j++) {
				// get the rgb value for current pixel
				$rgb = imagecolorat($im, $i, $j);
				
				// extract each value for r, g, b
				$rr = ($rgb >> 16) & 0xFF;
				$gg = ($rgb >> 8) & 0xFF;
				$bb = $rgb & 0xFF;
				
				// get the Value from the RGB value
				$g = round(($rr + $gg + $bb) / 3);
				
				// grayscale values have r=g=b=g
				$val = imagecolorallocate($im, $g, $g, $g);
				
				// set the gray value
				imagesetpixel ($im, $i, $j, $val);
			}
		}	
	}
	
	function deleteFile($path){
		if(file_exists($path)) unlink($path);
		if($this->opts[THUMBS]!=0){
			for($i=0,$n=$this->opts[THUMBS];$i<$n;$i++){
				$thumbPath=str_replace($this->opts[PREFIX],$this->opts[THUMB_PREFIX.$i],$path);
				if(file_exists($thumbPath)) 
					unlink($thumbPath);
			}
		}
	}
};
?>
