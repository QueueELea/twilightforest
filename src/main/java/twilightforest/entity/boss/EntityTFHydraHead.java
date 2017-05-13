package twilightforest.entity.boss;

import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;

public class EntityTFHydraHead extends EntityTFHydraPart {

    private static final DataParameter<Byte> DATA_MOUTH_POSITION = EntityDataManager.createKey(EntityTFHydraHead.class, DataSerializers.BYTE);
    private static final DataParameter<Byte> DATA_STATE = EntityDataManager.createKey(EntityTFHydraHead.class, DataSerializers.BYTE);

    public EntityTFHydraHead(World world)
    {
    	super(world);
		//texture = TwilightForestMod.MODEL_DIR + "hydra4.png";
		
		// the necks draw with the head, so we just draw the head at all times, sorry
		this.ignoreFrustumCheck = true;
    }

	public EntityTFHydraHead(EntityTFHydra hydra, String s, float f, float f1) {
		super(hydra, s, f, f1);
	}
	
	
	/**
	 * Rather than speed, this seems to control how far up or down the heads can tilt?
	 */
	@Override
    public int getVerticalFaceSpeed()
    {
        return 500;
    }
	
	/**
	 * We have our own custom death here.  In any case, we don't actually ever die, despawn and spew out XP orbs, so don't do that 
	 */
	@Override
	protected void onDeathUpdate() {
        ++this.deathTime;
	}

	@Override
    protected void entityInit()
    {
        super.entityInit();
        dataManager.register(DATA_MOUTH_POSITION, (byte) 0);
        dataManager.register(DATA_STATE, (byte) 0);
    }
	
	
    public float getMouthOpen()
    {
        return (dataManager.get(DATA_MOUTH_POSITION) & 0xFF) / 255.0F;
    }

    public int getState()
    {
        return dataManager.get(DATA_STATE) & 0xFF;
    }

    public void setMouthOpen(float openness)
    {
    	// bounds
    	if (openness < 0.0F)
    	{
    		openness = 0.0F;
    	}
    	if (openness > 1.0F)
    	{
    		openness = 1.0F;
    	}
    	
    	int openByte = Math.round(openness * 255);
    	
    	openByte &= 0xFF;
        dataManager.set(DATA_MOUTH_POSITION, (byte) openByte);
    }

    public void setState(int state)
    {
    	state &= 0xFF;
        dataManager.set(DATA_STATE, (byte) state);
    }

}
