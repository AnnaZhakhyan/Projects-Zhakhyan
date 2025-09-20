extends Control

func _ready():
	$Menu/AnimationPlayer.play("slide") 

#will this reload the battle page?
func _on_back_button_pressed() -> void:
	$".".queue_free()


func _on_forfeit_button_pressed() -> void:
	get_tree().change_scene_to_file("res://scenes/main-menu/MainScene.tscn")
	get_tree().root.get_node("Battlegrounds").queue_free()
