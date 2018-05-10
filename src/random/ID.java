package random;

/**
 * 
 * This enumemeration defines game object ID tags.
 * 
 * @author Shayan
 *
 */
public enum ID {
	
	// Player
	Player,
	
	// Token items
	Coin,
	LifeToken,
	HealthToken,
	AmmoToken,
	
	// Power up items
	SpeedUp,
	SlowMo,
	Freeze,
	Invincibility,
	
	// Upgrade items
	HealthBoost,
	SpeedBoost,
	DamageBoost,
	AmmoBoost,
	
	// Basic enemy types
	BounceEnemy,
	FlipEnemy,
	HorizontalEnemy,
	VerticalEnemy,
	SmallEnemy,
	BigEnemy,
	
	// Advanced enemy types
	SeekEnemy,
	MissleEnemy,
	BombEnemy,
	GuardEnemy,
	
	// Weapons
	Missle,
	SeekMissle,
	Bomb;
	
}
