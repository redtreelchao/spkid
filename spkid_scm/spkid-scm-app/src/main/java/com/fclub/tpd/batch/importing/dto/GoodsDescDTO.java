/**
 * f-club.cn
 * Copyright (c) 2009-2013 All Rights Reserved.
 */
package com.fclub.tpd.batch.importing.dto;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 
 * @author likaiping
 * @version $Id: GoodsDescDTO.java, v 0.1 Jul 1, 2013 11:14:54 AM likaiping Exp $
 */
public class GoodsDescDTO {

    //==========================商品描述（新）=============================//
      /** 成分 */
      protected String        desc_composition;
      /** 尺寸规格 */
      protected String        desc_dimensions;
      /** 材质 */
      protected String        desc_material;
      /** 防水性 */
      protected String        desc_waterproof;
      /** 适合人群 */
      protected String        desc_crowd;
      /** 温馨提示 */
      protected String        desc_notes;
      
      public String getDesc_composition() {
          return desc_composition;
      }

      public void setDesc_composition(String desc_composition) {
          this.desc_composition = desc_composition;
      }

      public String getDesc_dimensions() {
          return desc_dimensions;
      }

      public void setDesc_dimensions(String desc_dimensions) {
          this.desc_dimensions = desc_dimensions;
      }

      public String getDesc_material() {
          return desc_material;
      }

      public void setDesc_material(String desc_material) {
          this.desc_material = desc_material;
      }

      public String getDesc_waterproof() {
          return desc_waterproof;
      }

      public void setDesc_waterproof(String desc_waterproof) {
          this.desc_waterproof = desc_waterproof;
      }

      public String getDesc_crowd() {
          return desc_crowd;
      }

      public void setDesc_crowd(String desc_crowd) {
          this.desc_crowd = desc_crowd;
      }

      public String getDesc_notes() {
          return desc_notes;
      }

      public void setDesc_notes(String desc_notes) {
          this.desc_notes = desc_notes;
      }

      @Override
      public String toString() {
          return ReflectionToStringBuilder.toString(this, ToStringStyle.DEFAULT_STYLE);
      }
}
