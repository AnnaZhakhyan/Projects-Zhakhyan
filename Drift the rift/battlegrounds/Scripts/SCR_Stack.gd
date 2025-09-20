extends Node
class_name Stack

@export var stackName : String;
@export var value : int;

var chara : Chara 

func _ready() -> void:
	pass

func _init(targetChara : Chara, Name : String, Value : int) -> void:
	self.chara = targetChara
	self.stackName = Name
	self.value = Value
	pass
