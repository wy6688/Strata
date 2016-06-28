/**
 * Copyright (C) 2015 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.product.index.type;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.joda.beans.Bean;
import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.ImmutableBean;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectFieldsBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.opengamma.strata.basics.ReferenceData;
import com.opengamma.strata.basics.index.IborIndex;
import com.opengamma.strata.collect.ArgChecker;
import com.opengamma.strata.product.SecurityId;
import com.opengamma.strata.product.index.IborFutureTrade;

/**
 * A template for creating an Ibor Future trade using a relative definition of time.
 * <p>
 * The future is selected based on a minimum period and a sequence number.
 * Given a date, the minimum period is added, and then a futures contract is selected
 * according the the sequence number.
 */
@BeanDefinition(builderScope = "private")
final class RelativeIborFutureTemplate
    implements IborFutureTemplate, ImmutableBean, Serializable {

  /**
   * The minimum period between the value date and the first future.
   * <p>
   * This is used to select a future that is at least this period of time after the value date.
   * For example, the 2nd future of the series where the 1st future is at least 1 week after the value date
   * would be represented by a minimum period of 1 week and sequence number 2.
   */
  @PropertyDefinition(validate = "notNull")
  private final Period minimumPeriod;
  /**
   * The sequence number of the futures.
   * <p>
   * This is used to select the nth future after the value date.
   * For example, the 2nd future of the series where the 1st future is at least 1 week after the value date
   * would be represented by a minimum period of 1 week and sequence number 2.
   */
  @PropertyDefinition(validate = "ArgChecker.notNegativeOrZero")
  private final int sequenceNumber;
  /**
   * The underlying futures convention.
   * <p>
   * This specifies the market convention of the Ibor Futures to be created.
   */
  @PropertyDefinition(validate = "notNull")
  private final IborFutureConvention convention;

  //-------------------------------------------------------------------------
  /**
   * Obtains a template based on the specified convention.
   * <p>
   * The specific future is defined by two date-related inputs, the minimum period and the 1-based future number.
   * For example, the 2nd future of the series where the 1st future is at least 1 week after the value date
   * would be represented by a minimum period of 1 week and future number 2.
   * 
   * @param minimumPeriod  the minimum period between the base date and the first future
   * @param sequenceNumber  the 1-based index of the future after the minimum period, must be 1 or greater
   * @param convention  the future convention
   * @return the template
   */
  public static RelativeIborFutureTemplate of(Period minimumPeriod, int sequenceNumber, IborFutureConvention convention) {
    return new RelativeIborFutureTemplate(minimumPeriod, sequenceNumber, convention);
  }

  //-------------------------------------------------------------------------
  @Override
  public IborIndex getIndex() {
    return convention.getIndex();
  }

  @Override
  public IborFutureTrade createTrade(
      LocalDate tradeDate,
      SecurityId securityId,
      double quantity,
      double notional,
      double price,
      ReferenceData refData) {

    return convention.createTrade(tradeDate, securityId, minimumPeriod, sequenceNumber, quantity, notional, price, refData);
  }

  @Override
  public LocalDate calculateReferenceDateFromTradeDate(LocalDate tradeDate, ReferenceData refData) {
    return convention.calculateReferenceDateFromTradeDate(tradeDate, minimumPeriod, sequenceNumber, refData);
  }

  @Override
  public double approximateMaturity(LocalDate valuationDate) {
    return minimumPeriod.plus(convention.getIndex().getTenor()).toTotalMonths() / 12d;
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code RelativeIborFutureTemplate}.
   * @return the meta-bean, not null
   */
  public static RelativeIborFutureTemplate.Meta meta() {
    return RelativeIborFutureTemplate.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(RelativeIborFutureTemplate.Meta.INSTANCE);
  }

  /**
   * The serialization version id.
   */
  private static final long serialVersionUID = 1L;

  private RelativeIborFutureTemplate(
      Period minimumPeriod,
      int sequenceNumber,
      IborFutureConvention convention) {
    JodaBeanUtils.notNull(minimumPeriod, "minimumPeriod");
    ArgChecker.notNegativeOrZero(sequenceNumber, "sequenceNumber");
    JodaBeanUtils.notNull(convention, "convention");
    this.minimumPeriod = minimumPeriod;
    this.sequenceNumber = sequenceNumber;
    this.convention = convention;
  }

  @Override
  public RelativeIborFutureTemplate.Meta metaBean() {
    return RelativeIborFutureTemplate.Meta.INSTANCE;
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
   * Gets the minimum period between the value date and the first future.
   * <p>
   * This is used to select a future that is at least this period of time after the value date.
   * For example, the 2nd future of the series where the 1st future is at least 1 week after the value date
   * would be represented by a minimum period of 1 week and sequence number 2.
   * @return the value of the property, not null
   */
  public Period getMinimumPeriod() {
    return minimumPeriod;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the sequence number of the futures.
   * <p>
   * This is used to select the nth future after the value date.
   * For example, the 2nd future of the series where the 1st future is at least 1 week after the value date
   * would be represented by a minimum period of 1 week and sequence number 2.
   * @return the value of the property
   */
  public int getSequenceNumber() {
    return sequenceNumber;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the underlying futures convention.
   * <p>
   * This specifies the market convention of the Ibor Futures to be created.
   * @return the value of the property, not null
   */
  public IborFutureConvention getConvention() {
    return convention;
  }

  //-----------------------------------------------------------------------
  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      RelativeIborFutureTemplate other = (RelativeIborFutureTemplate) obj;
      return JodaBeanUtils.equal(minimumPeriod, other.minimumPeriod) &&
          (sequenceNumber == other.sequenceNumber) &&
          JodaBeanUtils.equal(convention, other.convention);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash = hash * 31 + JodaBeanUtils.hashCode(minimumPeriod);
    hash = hash * 31 + JodaBeanUtils.hashCode(sequenceNumber);
    hash = hash * 31 + JodaBeanUtils.hashCode(convention);
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(128);
    buf.append("RelativeIborFutureTemplate{");
    buf.append("minimumPeriod").append('=').append(minimumPeriod).append(',').append(' ');
    buf.append("sequenceNumber").append('=').append(sequenceNumber).append(',').append(' ');
    buf.append("convention").append('=').append(JodaBeanUtils.toString(convention));
    buf.append('}');
    return buf.toString();
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code RelativeIborFutureTemplate}.
   */
  public static final class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code minimumPeriod} property.
     */
    private final MetaProperty<Period> minimumPeriod = DirectMetaProperty.ofImmutable(
        this, "minimumPeriod", RelativeIborFutureTemplate.class, Period.class);
    /**
     * The meta-property for the {@code sequenceNumber} property.
     */
    private final MetaProperty<Integer> sequenceNumber = DirectMetaProperty.ofImmutable(
        this, "sequenceNumber", RelativeIborFutureTemplate.class, Integer.TYPE);
    /**
     * The meta-property for the {@code convention} property.
     */
    private final MetaProperty<IborFutureConvention> convention = DirectMetaProperty.ofImmutable(
        this, "convention", RelativeIborFutureTemplate.class, IborFutureConvention.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "minimumPeriod",
        "sequenceNumber",
        "convention");

    /**
     * Restricted constructor.
     */
    private Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case -1855508625:  // minimumPeriod
          return minimumPeriod;
        case -1353995670:  // sequenceNumber
          return sequenceNumber;
        case 2039569265:  // convention
          return convention;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends RelativeIborFutureTemplate> builder() {
      return new RelativeIborFutureTemplate.Builder();
    }

    @Override
    public Class<? extends RelativeIborFutureTemplate> beanType() {
      return RelativeIborFutureTemplate.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code minimumPeriod} property.
     * @return the meta-property, not null
     */
    public MetaProperty<Period> minimumPeriod() {
      return minimumPeriod;
    }

    /**
     * The meta-property for the {@code sequenceNumber} property.
     * @return the meta-property, not null
     */
    public MetaProperty<Integer> sequenceNumber() {
      return sequenceNumber;
    }

    /**
     * The meta-property for the {@code convention} property.
     * @return the meta-property, not null
     */
    public MetaProperty<IborFutureConvention> convention() {
      return convention;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case -1855508625:  // minimumPeriod
          return ((RelativeIborFutureTemplate) bean).getMinimumPeriod();
        case -1353995670:  // sequenceNumber
          return ((RelativeIborFutureTemplate) bean).getSequenceNumber();
        case 2039569265:  // convention
          return ((RelativeIborFutureTemplate) bean).getConvention();
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
   * The bean-builder for {@code RelativeIborFutureTemplate}.
   */
  private static final class Builder extends DirectFieldsBeanBuilder<RelativeIborFutureTemplate> {

    private Period minimumPeriod;
    private int sequenceNumber;
    private IborFutureConvention convention;

    /**
     * Restricted constructor.
     */
    private Builder() {
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      switch (propertyName.hashCode()) {
        case -1855508625:  // minimumPeriod
          return minimumPeriod;
        case -1353995670:  // sequenceNumber
          return sequenceNumber;
        case 2039569265:  // convention
          return convention;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
    }

    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case -1855508625:  // minimumPeriod
          this.minimumPeriod = (Period) newValue;
          break;
        case -1353995670:  // sequenceNumber
          this.sequenceNumber = (Integer) newValue;
          break;
        case 2039569265:  // convention
          this.convention = (IborFutureConvention) newValue;
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
    public RelativeIborFutureTemplate build() {
      return new RelativeIborFutureTemplate(
          minimumPeriod,
          sequenceNumber,
          convention);
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(128);
      buf.append("RelativeIborFutureTemplate.Builder{");
      buf.append("minimumPeriod").append('=').append(JodaBeanUtils.toString(minimumPeriod)).append(',').append(' ');
      buf.append("sequenceNumber").append('=').append(JodaBeanUtils.toString(sequenceNumber)).append(',').append(' ');
      buf.append("convention").append('=').append(JodaBeanUtils.toString(convention));
      buf.append('}');
      return buf.toString();
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}