extends Node

func play_voice(chara : Chara, VOType : int):
	var VO : VoiceOver = chara.VOICE_OVER
	
	match VOType:
		VoiceOver.TYPE.HURT : $VO.stream = VO.hurtVO[randi_range(0,VO.hurtVO.size()-1)]
		VoiceOver.TYPE.SWITCH : $VO.stream = VO.switchVO[randi_range(0,VO.switchVO.size()-1)]
		VoiceOver.TYPE.SWITCH_IN : $VO.stream = VO.switchInVO[randi_range(0,VO.switchInVO.size()-1)]
		VoiceOver.TYPE.SPLASH : $VO.stream = VO.splashVO[randi_range(0,VO.splashVO.size()-1)]
	
	$VO.play()

func play_sfx(sfx : AudioStream):
	$SFX.stream = sfx;
	$SFX.play()

func play_bgm(bgm : AudioStream):
	$BGM.stream = bgm;
	$BGM.play()
