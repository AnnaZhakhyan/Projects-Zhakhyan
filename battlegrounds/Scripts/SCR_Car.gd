extends TextureRect
class_name Car

var carOwner : Chara
var carSpeed : int = -1

func flash():
	$AnimationPlayer.play("FLASH")
