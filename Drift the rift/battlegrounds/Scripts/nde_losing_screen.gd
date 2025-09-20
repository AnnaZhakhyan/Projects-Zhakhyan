extends Control


# Called when the node enters the scene tree for the first time.
func _ready() -> void:
	$AnimationPlayer.play("losing")
	$AnimationPlayer2.play("jiggle")




func _on_restart_button_pressed() -> void:
	get_tree().change_scene_to_file("res://scenes/battlegrounds/NDE_Battlegrounds.tscn")


func _on_back_button_pressed() -> void:
	get_tree().change_scene_to_file("res://scenes/main-menu/MainScene.tscn")
	get_tree().root.get_node("Battlegrounds").queue_free()


func _on_back_label_mouse_entered() -> void:
	pass # Replace with function body.
