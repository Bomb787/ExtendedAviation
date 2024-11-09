package io.github.bomb787.extended_aviation.entity;

import immersive_aircraft.entity.AircraftEntity;
import immersive_aircraft.entity.Rotorcraft;
import immersive_aircraft.item.upgrade.VehicleStat;
import io.github.bomb787.extended_aviation.init.ItemInit;
import io.github.bomb787.extended_aviation.init.SoundInit;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class RamielEntity extends Rotorcraft {

    private byte soundCooldown;
    private byte shootCooldown;
    private final Predicate<Entity> SHOULD_TARGET = entity -> ((entity.getSoundSource() == SoundSource.HOSTILE && !(entity instanceof NeutralMob)) || (entity instanceof Projectile projectile && !Objects.requireNonNullElse(projectile.getOwner(), projectile).is(this.getControllingPassenger())));

    public RamielEntity(EntityType<? extends AircraftEntity> entityType, Level world) {
        super(entityType, world, false);
        this.adaptPlayerRotation = false;
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide) {
            if(this.soundCooldown == 0 && this.isVehicle() && (this.getX() - this.lastX != 0 || this.getZ() - this.lastZ != 0)) {
                this.level().playSound(this, this.blockPosition(), SoundInit.ANGELIC_CHORUS.get(), this.getSoundSource(), 0.5f, 1.0f);
                this.soundCooldown = 60;
            } else if(this.soundCooldown > 0) {
                this.soundCooldown--;
            }
            //Not very sure if this is actually good code :D
            if(this.isVehicle() && this.shootCooldown == 0) {
                //Gets all entities in a 10x10x10 area
                List<Entity> list = this.level().getEntitiesOfClass(Entity.class, AABB.ofSize(this.getBoundingBox().getCenter(), 10, 10, 10), SHOULD_TARGET);
                if(!list.isEmpty()) {
                    byte a = 0;
                    Vec3 centerPos = this.getBoundingBox().getCenter();
                    for(Entity entity : list) {
                        Vec3 targetPos = entity.getBoundingBox().getCenter();
                        Vec3 distanceTo = targetPos.subtract(centerPos);
                        //Makes sure there is a line of sight and the target is within 10 blocks
                        if(level().clip(new ClipContext(centerPos, targetPos, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, this)).getType() != HitResult.Type.BLOCK && distanceTo.lengthSqr() <= 100) {
                            if(a == 0) {
                                //Make sure this only happens once
                                this.shootCooldown = 100;
                                this.level().playSound(this, this.blockPosition(), SoundInit.LASER.get(), this.getSoundSource(), 1.5f, 1.0f);
                                a++;
                            }
                            //Modified sonic blast particle code.
                            Vec3 normal = distanceTo.normalize();
                            for(int i = 1; i < Mth.floor(distanceTo.length()) + 31; ++i) {
                                Vec3 vec33 = centerPos.add(normal.scale(i));
                                ((ServerLevel) this.level()).sendParticles(ParticleTypes.ELECTRIC_SPARK, vec33.x, vec33.y, vec33.z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
                            }
                            if(entity instanceof Projectile) {
                                //Vanilla projectiles can't be damaged
                                entity.remove(RemovalReason.DISCARDED);
                            } else {
                                entity.setSecondsOnFire(10);
                                entity.hurt(this.damageSources().magic(), 50);
                            }
                        }
                    }
                }
            } else if(this.shootCooldown > 0) {
                this.shootCooldown--;
            }
        }
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        nbt.getByte("shootCooldown");
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putByte("shootCooldown", this.shootCooldown);
    }

    @Override
    protected float getInputInterpolationSteps() {
        return 5;
    }

    //Need to do it differently so it doesn't play every tick.
    @Override
    protected SoundEvent getEngineSound() {
        return SoundEvents.EMPTY;
    }

    @Override
    public Item asItem() {
        return ItemInit.RAMIEL.get();
    }

    @Override
    public GUI_STYLE getGuiStyle() {
        return GUI_STYLE.NONE;
    }

    @Override
    protected float getGravity() {
        return wasTouchingWater ? 0.04f : (1.0f - getEnginePower()) * super.getGravity();
    }

    @Override
    public String getFuelType() {
        return "infinite";
    }

    @Override
    public boolean isFuelLow() {
        return false;
    }

    @Override
    protected void updateController() {
        if (canTurnOnEngine(getControllingPassenger())) {
            setEngineTarget(1.0f);
        }

        // forwards-backwards
        if (!onGround()) {
            setXRot(getXRot() + getProperties().get(VehicleStat.PITCH_SPEED) * pressingInterpolatedZ.getSmooth());
        }
        setXRot(getXRot() * (1.0f - getProperties().getAdditive(VehicleStat.STABILIZER)));

        // up and down
        setDeltaMovement(getDeltaMovement().add(0.0f, getEnginePower() * getProperties().get(VehicleStat.VERTICAL_SPEED) * pressingInterpolatedY.getSmooth(), 0.0f));

        // Rotate to pilot's head rotation
        Entity pilot = getControllingPassenger();
        if (pilot != null) {
            float diff = pilot.getYHeadRot() - getYRot();
            if (diff > 180.0f) {
                diff -= 360.0f;
            } else if (diff < -180.0f) {
                diff += 360.0f;
            }
            diff = diff * getProperties().get(VehicleStat.YAW_SPEED);
            if (Math.abs(diff) < 60f) {
                setYRot(getYRot() + diff);
            }
        }

        float thrust = (float) (Math.pow(getEnginePower(), 5.0) * getProperties().get(VehicleStat.ENGINE_SPEED));

        // left and right
        Vector3f direction = getRightDirection().mul(thrust * pressingInterpolatedX.getSmooth());
        setDeltaMovement(getDeltaMovement().add(direction.x, direction.y, direction.z));

        // forward and backward
        direction = getForwardDirection().mul(thrust * pressingInterpolatedZ.getSmooth());
        setDeltaMovement(getDeltaMovement().add(direction.x, direction.y, direction.z));
    }

    @Override
    public float getFuelUtilization() {
        return 1f;
    }

    @Override
    public double getZoom() {
        return 2.0;
    }

    @Override
    protected SoundEvent getEngineStartSound() {
        return SoundEvents.EMPTY;
    }

}