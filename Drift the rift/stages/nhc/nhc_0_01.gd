extends LevelSetup

func level_setup():
	
	Backdrop = load("res://art/bg/Plains/plains.tscn")
	
	Enemies.append(EnemyRes.new(
		"Misako","Street Rift Drifter",Utils.COLOR.STR,
		[86,56,56,56,250,4444,0,33],
		load("res://chara/enm/enm_000_FRAMES.tres"),
		load("res://chara/mis/mis_000_SPLASH.png"),
		null,
		MiscCharaValues.new(Vector2(-59.0,-18.0)),
		[
			EnemyCardRes.new("Misako","Bad Swipe",4,Utils.COLOR.STR,Utils.TYPE.PHYSICAL,"Did you even\nsay thank you?"),
			EnemyCardRes.new("Misako","Bad Swipe",4,Utils.COLOR.STR,Utils.TYPE.PHYSICAL,"Did you even\nsay thank you?"),
			EnemyCardRes.new("Misako","Evil Strike",3,Utils.COLOR.STR,Utils.TYPE.PHYSICAL,"Did you even\nsay thank you?"),
			EnemyCardRes.new("Misako","Bad Swipe",4,Utils.COLOR.STR,Utils.TYPE.PHYSICAL,"Did you even\nsay thank you?"),
			EnemyCardRes.new("Misako","No Good Deeds",5,Utils.COLOR.STR,Utils.TYPE.PHYSICAL,"Did you even\nsay thank you?")
		], []))
