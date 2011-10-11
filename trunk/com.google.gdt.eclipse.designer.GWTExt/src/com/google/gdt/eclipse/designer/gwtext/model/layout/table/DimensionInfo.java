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
package com.google.gdt.eclipse.designer.gwtext.model.layout.table;

import com.google.gdt.eclipse.designer.model.widgets.WidgetInfo;

/**
 * Information about single column/row in {@link TableLayoutInfo}.
 * 
 * @author scheglov_ke
 * @coverage GWTExt.model.layout
 */
public abstract class DimensionInfo {
  protected final TableLayoutInfo m_panel;

  ////////////////////////////////////////////////////////////////////////////
  //
  // Constructor
  //
  ////////////////////////////////////////////////////////////////////////////
  public DimensionInfo(TableLayoutInfo panel) {
    m_panel = panel;
  }

  ////////////////////////////////////////////////////////////////////////////
  //
  // Access
  //
  ////////////////////////////////////////////////////////////////////////////
  /**
   * @return the index of this {@link DimensionInfo} in array of all dimensions.
   */
  public abstract int getIndex();

  /**
   * @return <code>true</code> if this {@link DimensionInfo} contains no {@link WidgetInfo}'s.
   */
  public abstract boolean isEmpty();

  /**
   * @return the title to display to user.
   */
  public abstract String getTitle();

  /**
   * Deletes this {@link DimensionInfo}.
   */
  public abstract void delete() throws Exception;
}