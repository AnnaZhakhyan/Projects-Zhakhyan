extends Control

func reset():
	create_tween().tween_property($Chara,"position",Vector2(158.829,141.0),0.3).set_trans(Tween.TRANS_SINE).set_ease(Tween.EASE_IN_OUT)
	create_tween().tween_property($Chara2,"position",Vector2(224.829,55),0.3).set_trans(Tween.TRANS_SINE).set_ease(Tween.EASE_IN_OUT)
	create_tween().tween_property($Chara3,"position",Vector2(97.829,243.0),0.3).set_trans(Tween.TRANS_SINE).set_ease(Tween.EASE_IN_OUT)
	
func bring_forth(chara : Chara):
	create_tween().tween_property(chara,"position:x",250.829,0.3).set_trans(Tween.TRANS_SINE).set_ease(Tween.EASE_IN_OUT)
	if(chara != $Chara): create_tween().tween_property($Chara,"position:x",98.0,0.3).set_trans(Tween.TRANS_SINE).set_ease(Tween.EASE_IN_OUT)
	if(chara != $Chara2): create_tween().tween_property($Chara2,"position:x",98.0,0.3).set_trans(Tween.TRANS_SINE).set_ease(Tween.EASE_IN_OUT)
	if(chara != $Chara3): create_tween().tween_property($Chara3,"position:x",98.0,0.3).set_trans(Tween.TRANS_SINE).set_ease(Tween.EASE_IN_OUT)
