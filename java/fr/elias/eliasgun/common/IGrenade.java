package fr.elias.eliasgun.common;

public interface IGrenade
{
	public static enum EnumGrenadeType
	{
		FRAG("frag"),
		FLASHBANG("flashbang"),
		SMOKE("smoke"),
		STICKY("sticky"),
		MOLOTOV("molotov");
		
		public String name;
		
		EnumGrenadeType(String s)
		{
			name = s;
		}
	}
	public EnumGrenadeType getGrenadeType();
}
