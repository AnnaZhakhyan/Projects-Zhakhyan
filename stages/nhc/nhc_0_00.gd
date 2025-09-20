extends LevelSetup

func level_setup():
	
	Backdrop = load("res://art/bg/Plains/plains.tscn")
	
	Enemies.append(EnemyRes.new(
		"Baddy1","The Bad Guy",Utils.COLOR.STR,
		[86,56,56,56,250,1000,0,0],
		load("res://chara/enm/enm_000_FRAMES.tres"),
		load("res://chara/enm/enm_000_SPLASH.png"),
		load("res://chara/enm/enm_000_vo.tres"),
		MiscCharaValues.new(Vector2(-59.0,-18.0)),
		[
			EnemyCardRes.new("Baddy1","Evil Strike",3,Utils.COLOR.STR,Utils.TYPE.PHYSICAL,"Did you even\nsay thank you?"),
			EnemyCardRes.new("Baddy1","Bad Swipe",4,Utils.COLOR.STR,Utils.TYPE.PHYSICAL,"Did you even\nsay thank you?"),
			EnemyCardRes.new("Baddy1","No Good Deeds",5,Utils.COLOR.STR,Utils.TYPE.PHYSICAL,"Did you even\nsay thank you?")
		], []))
	
	Enemies.append(EnemyRes.new(
		"Baddy2","The Bad Guy",Utils.COLOR.PHY,
		[86,56,56,56,200,1000,0,0],
		load("res://chara/enm/enm_000_FRAMES.tres"),
		load("res://chara/enm/enm_000_SPLASH.png"),
		load("res://chara/enm/enm_000_vo.tres"),
		MiscCharaValues.new(Vector2(-59.0,-18.0)),
		[
			EnemyCardRes.new("Baddy2","Evil Strike",3,Utils.COLOR.PHY,Utils.TYPE.PHYSICAL,"Did you even\nsay thank you?"),
			EnemyCardRes.new("Baddy2","Bad Swipe",4,Utils.COLOR.PHY,Utils.TYPE.SPECIAL,"Did you even\nsay thank you?"),
			EnemyCardRes.new("Baddy2","No Good Deeds",5,Utils.COLOR.PHY,Utils.TYPE.PHYSICAL,"Did you even\nsay thank you?")
		], []))
	
	Enemies.append(EnemyRes.new(
		"Baddy3","The Bad Guy",Utils.COLOR.INT,
		[86,56,56,56,472,1000,0,0],
		load("res://chara/enm/enm_000_FRAMES.tres"),
		load("res://chara/enm/enm_000_SPLASH.png"),
		load("res://chara/enm/enm_000_vo.tres"),
		MiscCharaValues.new(Vector2(-59.0,-18.0)),
		[
			EnemyCardRes.new("Baddy3","Evil Strike",3,Utils.COLOR.INT,Utils.TYPE.PHYSICAL,"Did you even\nsay thank you?"),
			EnemyCardRes.new("Baddy3","Bad Swipe",4,Utils.COLOR.INT,Utils.TYPE.SPECIAL,"Did you even\nsay thank you?"),
			EnemyCardRes.new("Baddy3","No Good Deeds",5,Utils.COLOR.INT,Utils.TYPE.SPECIAL,"Did you even\nsay thank you?")
		], []))
