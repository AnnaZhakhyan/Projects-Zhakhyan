extends LevelSetup


func level_setup():
	
	Backdrop = load("res://art/bg/BehindThrone/behind_the_throne.tscn")
	
	Enemies.append(EnemyRes.new(
		"King Kozi","The No Longer Prince of Kozi's Kingdom",Utils.COLOR.STR,
		[99,199,55,55,274,3333,10,15],
		load("res://chara/kzi/kzi_000_FRAMES.tres"),
		load("res://chara/kzi/kzi_000_SPLASH.png"),
		load("res://chara/kzi/kzi_000_VO.tres"),
		MiscCharaValues.new(Vector2(-62.0,-20.0)),
		[
			EnemyCardRes.new("King Kozi","Death Swing",8,Utils.COLOR.STR,Utils.TYPE.PHYSICAL,"Kneel Before Me!"),
			EnemyCardRes.new("King Kozi","Ego Death",4,Utils.COLOR.STR,Utils.TYPE.SPECIAL,"Execution!"),
			EnemyCardRes.new("King Kozi","Lost Pride",4,Utils.COLOR.STR,Utils.TYPE.PHYSICAL,"I won't fall!"),
			EnemyCardRes.new("King Kozi","All be Damned",2,Utils.COLOR.STR,Utils.TYPE.SPECIAL,"Off with your head!"),
			EnemyCardRes.new("King Kozi","Desprete Strike",2,Utils.COLOR.STR,Utils.TYPE.SPECIAL,"Nooooo!")
		],
		[222,111]))
