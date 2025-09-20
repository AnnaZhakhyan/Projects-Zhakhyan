extends Node
class_name CharaSet

var chara : CharaRes
var items : Array[ItemRes]

func _init(_chara, _items) -> void:
	chara = _chara
	items = _items
