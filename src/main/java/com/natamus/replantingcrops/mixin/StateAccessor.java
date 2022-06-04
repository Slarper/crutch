package com.natamus.replantingcrops.mixin;

import com.google.common.collect.ImmutableMap;
import net.minecraft.state.State;
import net.minecraft.state.property.Property;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(State.class)
public interface StateAccessor {
    @Accessor("entries")
    ImmutableMap<Property<?>, Comparable<?>> getEntries();
}
