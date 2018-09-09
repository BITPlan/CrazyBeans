/**
 * Copyright (c) 2001 Markus Dahm
 * Copyright (C) 2015 BITPlan GmbH
 *
 * http://www.bitplan.com
 * 
 * This source is part of
 * https://github.com/BITPlan/CrazyBeans
 * and the license as outlined there applies
 * 
 */
package cb.petal;

/**
 * convenience enumeration for visibilities
 * @author wf
 *
 */
public enum Visibility {

  PUBLIC("public"), PROTECTED("protected"), PRIVATE("private"), IMPLEMENTATION("implementation"), UNDEFINED(null);
  private String value;

  Visibility(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return this.getValue();
  }
}
