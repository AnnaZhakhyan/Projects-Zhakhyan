extends LevelSetup

func level_setup():
	
	Backdrop = load("res://art/bg/BehindThrone/behind_the_throne.tscn")
	
	Enemies.append(EnemyRes.new(
		"Orphan","Stupid Face",Utils.COLOR.INT,
		[1,1,1,1,411,111,0,0],
		load("res://chara/enm/enm_001_FRAMES.tres"),
		load("res://chara/enm/enm_001_SPLASH.png"),
		load("res://chara/enm/enm_001_vo.tres"),
		MiscCharaValues.new(Vector2(-59.0,-18.0)),
		[
			EnemyCardRes.new("Orhpan","No Parents",1,Utils.COLOR.INT,Utils.TYPE.PHYSICAL,"Why are you doing this?"),
			EnemyCardRes.new("Orhpan","No Loved Ones",1,Utils.COLOR.INT,Utils.TYPE.PHYSICAL,"Are you hear to adopt me?"),
			EnemyCardRes.new("Orhpan","No Future",1,Utils.COLOR.INT,Utils.TYPE.PHYSICAL,"Thank you sir. :p ")
		], []))
