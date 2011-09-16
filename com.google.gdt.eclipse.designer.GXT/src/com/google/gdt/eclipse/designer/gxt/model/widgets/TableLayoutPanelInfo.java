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
package com.google.gdt.eclipse.designer.gxt.model.widgets;

import com.google.gdt.eclipse.designer.gxt.model.layout.LayoutDataSupport;

import org.eclipse.wb.core.model.ObjectInfo;
import org.eclipse.wb.internal.core.model.creation.CreationSupport;
import org.eclipse.wb.internal.core.model.description.ComponentDescription;
import org.eclipse.wb.internal.core.utils.ast.AstEditor;

/**
 * Model for <code>com.extjs.gxt.ui.client.widget.layout.TableLayout</code> based panels.
 * 
 * @author scheglov_ke
 * @coverage ExtGWT.model
 */
public abstract class TableLayoutPanelInfo extends LayoutContainerInfo {
  ////////////////////////////////////////////////////////////////////////////
  //
  // Constructor
  //
  ////////////////////////////////////////////////////////////////////////////
  public TableLayoutPanelInfo(AstEditor editor,
      ComponentDescription description,
      CreationSupport creationSupport) throws Exception {
    super(editor, description, creationSupport);
    new LayoutDataSupport(this) {
      @Override
      public LayoutContainerInfo getContainer() {
        return TableLayoutPanelInfo.this;
      }

      @Override
      protected boolean isActiveOnContainer(ObjectInfo container) {
        return container == TableLayoutPanelInfo.this;
      }
    };
  }

  ////////////////////////////////////////////////////////////////////////////
  //
  // Access
  //
  ////////////////////////////////////////////////////////////////////////////
  @Override
  public boolean hasLayoutData() {
    return true;
  }
}