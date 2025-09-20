extends PanelContainer

var chara : CharaRes
var items : Array[ItemRes]

var chara_select : int

var is_opened : bool = false

func setup(charaSet : CharaSet):
	chara = charaSet.chara
	items = charaSet.items
	
	API.current_team[chara_select] = chara
	
	$SPLASH/OFFSET/TEX.texture = chara.SPLASH
	$SPLASH/OFFSET/TEX.position = chara.MISC.CarSplashOffset
	
	$DETAILS/STATS/ATK_STAT/BASE.text = str(chara.STATS.ATK)
	$DETAILS/STATS/S_ATK_STAT/BASE.text = str(chara.STATS.S_ATK)
	$DETAILS/STATS/DEF_STAT/BASE.text = str(chara.STATS.DEF)
	$DETAILS/STATS/S_DEF_STAT/BASE.text = str(chara.STATS.S_DEF)
	$DETAILS/STATS/HP_STAT/BASE.text = str(chara.STATS.HP)
	$DETAILS/STATS/SPD_STAT/BASE.text = str(chara.STATS.SPD)
	
	$INFO/NAME.text = chara.NAME
	
	match chara.COLOR:
		0: 
			$INFO/COLOR.text = "AGL"
			$INFO/COLOR.set("theme_override_colors/font_color",Color("4d79d6"))
		1: 
			$INFO/COLOR.text = "STR"
			$INFO/COLOR.set("theme_override_colors/font_color",Color("d6664d"))
		2: 
			$INFO/COLOR.text = "PHY"
			$INFO/COLOR.set("theme_override_colors/font_color",Color("d6a24d"))
		3: 
			$INFO/COLOR.text = "INT"
			$INFO/COLOR.set("theme_override_colors/font_color",Color("a44dd6"))
		4: 
			$INFO/COLOR.text = "TEQ"
			$INFO/COLOR.set("theme_override_colors/font_color",Color("22a12a"))
	# Items
	var items2 : Array[ItemRes] = charaSet.items
	# Special Item
	if items2.size() != 0: 
		$INFO/ITEM/ITEM.texture = items2[0].itemIcon
		items2.remove_at(0)
	# Basic Items
	for item in $INFO/S_ITEMS.get_children():
		item.queue_free()
	for item : ItemRes in items2:
		var itemBox = load("res://scenes/teamselect/NDE_BasicItem.tscn").instantiate()
		itemBox.get_node("Icon").texture = item.itemIcon
		$INFO/S_ITEMS.add_child(itemBox)
	
func _gui_input(event):
	if event is InputEventScreenTouch:		
		if event.is_pressed():
			$Timer.start()	
		elif event.is_released():
			$Timer.stop()
			if !is_opened:
				setup(await System.select_character())
			else:
				match chara_select:
					0: $AP.play("CLOSE_MIDDLE")
					1: $AP.play("CLOSE_LEFT")
					2: $AP.play("CLOSE_RIGHT")
				
				$DETAILS/ITEM_DESC.dissapear()
				$DETAILS/MINI_DESC.dissapear()
				$DETAILS/MINI_DESC2.dissapear()
				$DETAILS/MINI_DESC3.dissapear()
				$DETAILS/MINI_DESC4.dissapear()
				$DETAILS/MINI_DESC5.dissapear()
	
				await $AP.animation_finished
				z_index = 0
				is_opened = false

func _on_timer_timeout():
	is_opened = true
	z_index = 1
	match chara_select:
		0: $AP.play("OPEN_MIDDLE")
		1: $AP.play("OPEN_LEFT")
		2: $AP.play("OPEN_RIGHT")
		
	$DETAILS/ITEM_DESC.appear()
	$DETAILS/MINI_DESC.appear()
	$DETAILS/MINI_DESC2.appear()
	$DETAILS/MINI_DESC3.appear()
	$DETAILS/MINI_DESC4.appear()
	$DETAILS/MINI_DESC5.appear()
	
