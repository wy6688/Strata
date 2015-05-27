/**
 * Copyright (C) 2015 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.engine.marketdata.config;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import org.joda.beans.Bean;
import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.ImmutableBean;
import org.joda.beans.ImmutableConstructor;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectFieldsBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.google.common.collect.ImmutableMap;
import com.opengamma.strata.collect.type.TypedString;

/**
 * Configuration required for building non-observable market data, for example curves or surfaces.
 * <p>
 * This class is effectively a map of arbitrary objects, keyed by their type and a name.
 */
@BeanDefinition(builderScope = "private")
public final class MarketDataConfig implements ImmutableBean {

  /** An empty set of market data configuration. */
  private static final MarketDataConfig EMPTY = new MarketDataConfig(ImmutableMap.of());

  /** The configuration objects, keyed by their type and name. */
  @PropertyDefinition(validate = "notNull", get = "private")
  private final ImmutableMap<Class<?>, SingleTypeMarketDataConfig> configs;

  /**
   * Returns an empty set of market data configuration.
   *
   * @return an empty set of market data configuration
   */
  public static MarketDataConfig empty() {
    return MarketDataConfig.EMPTY;
  }

  /**
   * Returns a mutable builder for building an instance of {@code MarketDataConfig}.
   *
   * @return a mutable builder for building an instance of {@code MarketDataConfig}
   */
  public static MarketDataConfigBuilder builder() {
    return new MarketDataConfigBuilder();
  }

  /**
   * Package-private constructor used by the builder.
   *
   * @param configs  sets of configuration keyed by their type
   */
  @ImmutableConstructor
  MarketDataConfig(Map<Class<?>, SingleTypeMarketDataConfig> configs) {
    JodaBeanUtils.notNull(configs, "configs");
    this.configs = ImmutableMap.copyOf(configs);
  }

  /**
   * Returns the configuration object with the specified type and name if available.
   *
   * @param type  the type of the configuration object
   * @param name  the name of the configuration object
   * @param <T>  the type of the configuration object
   * @return the configuration with the specified type and name
   * @throws IllegalArgumentException if no configuration is found with the specified type and name
   */
  @SuppressWarnings("unchecked")
  public <T> Optional<T> get(Class<T> type, String name) {
    SingleTypeMarketDataConfig typeConfigs = configs.get(type);

    if (typeConfigs == null) {
      return Optional.empty();
    }
    return (Optional<T>) typeConfigs.get(name);
  }

  /**
   * Returns the configuration object with the specified type and name if available.
   *
   * @param type  the type of the configuration object
   * @param name  the name of the configuration object
   * @param <T>  the type of the configuration object
   * @return the configuration with the specified type and name
   * @throws IllegalArgumentException if no configuration is found with the specified type and name
   */
  @SuppressWarnings("unchecked")
  public <T> Optional<T> get(Class<T> type, TypedString<?> name) {
    SingleTypeMarketDataConfig typeConfigs = configs.get(type);

    if (typeConfigs == null) {
      return Optional.empty();
    }
    return (Optional<T>) typeConfigs.get(name.toString());
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code MarketDataConfig}.
   * @return the meta-bean, not null
   */
  public static MarketDataConfig.Meta meta() {
    return MarketDataConfig.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(MarketDataConfig.Meta.INSTANCE);
  }

  @Override
  public MarketDataConfig.Meta metaBean() {
    return MarketDataConfig.Meta.INSTANCE;
  }

  @Override
  public <R> Property<R> property(String propertyName) {
    return metaBean().<R>metaProperty(propertyName).createProperty(this);
  }

  @Override
  public Set<String> propertyNames() {
    return metaBean().metaPropertyMap().keySet();
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the configuration objects, keyed by their type and name.
   * @return the value of the property, not null
   */
  private ImmutableMap<Class<?>, SingleTypeMarketDataConfig> getConfigs() {
    return configs;
  }

  //-----------------------------------------------------------------------
  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      MarketDataConfig other = (MarketDataConfig) obj;
      return JodaBeanUtils.equal(getConfigs(), other.getConfigs());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash = hash * 31 + JodaBeanUtils.hashCode(getConfigs());
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(64);
    buf.append("MarketDataConfig{");
    buf.append("configs").append('=').append(JodaBeanUtils.toString(getConfigs()));
    buf.append('}');
    return buf.toString();
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code MarketDataConfig}.
   */
  public static final class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code configs} property.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private final MetaProperty<ImmutableMap<Class<?>, SingleTypeMarketDataConfig>> configs = DirectMetaProperty.ofImmutable(
        this, "configs", MarketDataConfig.class, (Class) ImmutableMap.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "configs");

    /**
     * Restricted constructor.
     */
    private Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 951117169:  // configs
          return configs;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends MarketDataConfig> builder() {
      return new MarketDataConfig.Builder();
    }

    @Override
    public Class<? extends MarketDataConfig> beanType() {
      return MarketDataConfig.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code configs} property.
     * @return the meta-property, not null
     */
    public MetaProperty<ImmutableMap<Class<?>, SingleTypeMarketDataConfig>> configs() {
      return configs;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 951117169:  // configs
          return ((MarketDataConfig) bean).getConfigs();
      }
      return super.propertyGet(bean, propertyName, quiet);
    }

    @Override
    protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
      metaProperty(propertyName);
      if (quiet) {
        return;
      }
      throw new UnsupportedOperationException("Property cannot be written: " + propertyName);
    }

  }

  //-----------------------------------------------------------------------
  /**
   * The bean-builder for {@code MarketDataConfig}.
   */
  private static final class Builder extends DirectFieldsBeanBuilder<MarketDataConfig> {

    private Map<Class<?>, SingleTypeMarketDataConfig> configs = ImmutableMap.of();

    /**
     * Restricted constructor.
     */
    private Builder() {
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      switch (propertyName.hashCode()) {
        case 951117169:  // configs
          return configs;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case 951117169:  // configs
          this.configs = (Map<Class<?>, SingleTypeMarketDataConfig>) newValue;
          break;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
      return this;
    }

    @Override
    public Builder set(MetaProperty<?> property, Object value) {
      super.set(property, value);
      return this;
    }

    @Override
    public Builder setString(String propertyName, String value) {
      setString(meta().metaProperty(propertyName), value);
      return this;
    }

    @Override
    public Builder setString(MetaProperty<?> property, String value) {
      super.setString(property, value);
      return this;
    }

    @Override
    public Builder setAll(Map<String, ? extends Object> propertyValueMap) {
      super.setAll(propertyValueMap);
      return this;
    }

    @Override
    public MarketDataConfig build() {
      return new MarketDataConfig(
          configs);
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(64);
      buf.append("MarketDataConfig.Builder{");
      buf.append("configs").append('=').append(JodaBeanUtils.toString(configs));
      buf.append('}');
      return buf.toString();
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
