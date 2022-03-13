package top.theillusivec4.champions.common.affix.core;

import java.lang.reflect.InvocationTargetException;
import net.minecraft.nbt.CompoundTag;
import top.theillusivec4.champions.Champions;
import top.theillusivec4.champions.api.IChampion;

public abstract class AffixData {

  private IChampion champion;
  private String identifier;

  protected AffixData() {
  }

  public void readData(IChampion champion, String identifier) {
    this.champion = champion;
    this.identifier = identifier;
    readFromNBT(champion.getServer().getData(identifier));
  }

  public abstract void readFromNBT(CompoundTag tag);

  public abstract CompoundTag writeToNBT();

  public void saveData() {
    champion.getServer().setData(identifier, writeToNBT());
  }

  public static <T extends AffixData> T getData(IChampion champion, String id, Class<T> clazz) {
    T data = null;

    try {
      data = clazz.getDeclaredConstructor().newInstance();
      data.readData(champion, id);
    } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
      Champions.LOGGER.error("Error reading data from class " + clazz);
    }
    return data;
  }

  public static class BooleanData extends AffixData {

    public boolean mode;

    @Override
    public void readFromNBT(CompoundTag tag) {
      mode = tag.getBoolean("mode");
    }

    @Override
    public CompoundTag writeToNBT() {
      CompoundTag compound = new CompoundTag();
      compound.putBoolean("mode", mode);
      return compound;
    }
  }

  public static class IntegerData extends AffixData {

    public int num;

    @Override
    public void readFromNBT(CompoundTag tag) {
      num = tag.getInt("num");
    }

    @Override
    public CompoundTag writeToNBT() {
      CompoundTag compound = new CompoundTag();
      compound.putInt("num", num);
      return compound;
    }
  }
}
