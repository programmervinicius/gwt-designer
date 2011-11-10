/*******************************************************************************
 * Copyright 2011 Google Inc. All Rights Reserved.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.google.gdt.eclipse.designer.gef.part;

import com.google.gdt.eclipse.designer.model.widgets.cell.ColumnInfo;

import org.eclipse.wb.core.gef.part.AbstractComponentEditPart;
import org.eclipse.wb.core.gef.policy.DirectTextPropertyEditPolicy;
import org.eclipse.wb.gef.core.EditPart;
import org.eclipse.wb.internal.core.model.property.Property;

/**
 * {@link EditPart} for {@link ColumnInfo}.
 * 
 * @author sablin_aa
 * @coverage gwt.gef.part
 */
public class ColumnEditPart extends AbstractComponentEditPart {
  private final ColumnInfo m_column;

  ////////////////////////////////////////////////////////////////////////////
  //
  // Constructor
  //
  ////////////////////////////////////////////////////////////////////////////
  public ColumnEditPart(ColumnInfo column) {
    super(column);
    m_column = column;
  }

  ////////////////////////////////////////////////////////////////////////////
  //
  // Policies
  //
  ////////////////////////////////////////////////////////////////////////////
  @Override
  protected void refreshEditPolicies() {
    super.refreshEditPolicies();
    // install direct text edit policy for "header" property
    {
      Property headerProperty = m_column.getHeaderProperty();
      DirectTextPropertyEditPolicy policy;
      if (headerProperty != null) {
        policy = new DirectTextPropertyEditPolicy(m_column, headerProperty);
      } else {
        policy = null;
      }
      installEditPolicy(DirectTextPropertyEditPolicy.KEY, policy);
    }
  }
}
