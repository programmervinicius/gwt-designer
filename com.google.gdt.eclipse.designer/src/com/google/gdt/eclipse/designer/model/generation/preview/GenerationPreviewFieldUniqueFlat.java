/*
 * Copyright 2011 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.gdt.eclipse.designer.model.generation.preview;

import org.eclipse.wb.internal.core.model.generation.GenerationPropertiesComposite;
import org.eclipse.wb.internal.core.model.generation.preview.GenerationPreview;
import org.eclipse.wb.internal.core.model.generation.statement.flat.FlatStatementGenerator;
import org.eclipse.wb.internal.core.model.variable.FieldUniqueVariableSupport;
import org.eclipse.wb.internal.core.model.variable.FieldVariableSupport;

/**
 * Implementation of {@link GenerationPreview} for {@link FieldUniqueVariableSupport} and
 * {@link FlatStatementGenerator}.
 * 
 * @author scheglov_ke
 * @coverage gwt.model.generation.ui
 */
public final class GenerationPreviewFieldUniqueFlat extends GenerationPreview {
  public static final GenerationPreview INSTANCE = new GenerationPreviewFieldUniqueFlat();

  ////////////////////////////////////////////////////////////////////////////
  //
  // Constructor
  //
  ////////////////////////////////////////////////////////////////////////////
  private GenerationPreviewFieldUniqueFlat() {
  }

  ////////////////////////////////////////////////////////////////////////////
  //
  // GenerationPreview
  //
  ////////////////////////////////////////////////////////////////////////////
  @Override
  public String getPreview(GenerationPropertiesComposite variableComposite,
      GenerationPropertiesComposite statementComposite) {
    boolean v_useThis = variableComposite.getBoolean(FieldUniqueVariableSupport.P_PREFIX_THIS);
    int v_modifierIndex = variableComposite.getInteger(FieldUniqueVariableSupport.P_FIELD_MODIFIER);
    String v_modifierSource = FieldVariableSupport.V_MODIFIER_CODE[v_modifierIndex];
    boolean s_usePrefix = statementComposite.getBoolean(FlatStatementGenerator.P_USE_PREFIX);
    String s_thePrefix = statementComposite.getString(FlatStatementGenerator.P_PREFIX_TEXT);
    //
    String source = "";
    String panelRef = v_useThis ? "this.panel" : "panel";
    String buttonRef = v_useThis ? "this.button" : "button";
    // declare fields
    source += "\t" + v_modifierSource + "FlowPanel panel;\n";
    source += "\t" + v_modifierSource + "Button button;\n";
    // begin
    source += "\t...\n";
    // parent
    {
      // assign field
      source += "\t" + panelRef + " = new FlowPanel();\n";
      // properties
      source += "\t" + panelRef + ".setEnabled(true);\n";
    }
    // child
    {
      // optional prefix
      if (s_usePrefix) {
        source += "\t" + s_thePrefix + "\n";
      }
      // assign field
      source += "\t" + buttonRef + " = new Button();\n";
      // properties
      source += "\t" + buttonRef + ".setText(\"Add customer...\");\n";
      source += "\t" + panelRef + ".add(" + buttonRef + ");\n";
    }
    // end
    source += "\t...\n";
    // final result
    return source;
  }
}