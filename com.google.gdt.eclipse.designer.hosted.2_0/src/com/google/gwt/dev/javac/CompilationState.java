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
package com.google.gwt.dev.javac;

import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.dev.javac.CompilationStateBuilder.CompileMoreLater;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Encapsulates the state of active compilation units in a particular module.
 * State is accumulated throughout the life cycle of the containing module and
 * may be invalidated at certain times and recomputed.
 */
public class CompilationState {

  /**
   * Classes mapped by binary name.
   */
  protected final Map<String, CompiledClass> classFileMap = new HashMap<String, CompiledClass>();

  /**
   * Classes mapped by source name.
   */
  protected final Map<String, CompiledClass> classFileMapBySource = new HashMap<String, CompiledClass>();

  /**
   * All my compilation units.
   */
  protected final Map<String, CompilationUnit> unitMap = new HashMap<String, CompilationUnit>();

  private final CompileMoreLater compileMoreLater;

  /**
   * Unmodifiable view of {@link #classFileMap).
   */
  private final Map<String, CompiledClass> exposedClassFileMap = Collections.unmodifiableMap(classFileMap);

  /**
   * Unmodifiable view of {@link #classFileMapBySource).
   */
  private final Map<String, CompiledClass> exposedClassFileMapBySource = Collections.unmodifiableMap(classFileMapBySource);

  /**
   * Unmodifiable view of {@link #unitMap}.
   */
  private final Map<String, CompilationUnit> exposedUnitMap = Collections.unmodifiableMap(unitMap);

  /**
   * Unmodifiable view of all units.
   */
  private final Collection<CompilationUnit> exposedUnits = Collections.unmodifiableCollection(unitMap.values());

  /**
   * Controls our type oracle.
   */
  private final TypeOracleMediator mediator = new TypeOracleMediator();

  CompilationState(TreeLogger logger, Collection<CompilationUnit> units,
      CompileMoreLater compileMoreLater) {
    this.compileMoreLater = compileMoreLater;
    assimilateUnits(logger, units);
  }

  public void addGeneratedCompilationUnits(TreeLogger logger,
      Collection<GeneratedUnit> generatedUnits) {
    logger = logger.branch(TreeLogger.DEBUG, "Adding '" + generatedUnits.size()
        + "' new generated units");
    Collection<CompilationUnit> newUnits = compileMoreLater.addGeneratedTypes(
        logger, generatedUnits);
    assimilateUnits(logger, newUnits);
  }

  /**
   * Returns a map of all compiled classes by binary name.
   */
  public Map<String, CompiledClass> getClassFileMap() {
    return exposedClassFileMap;
  }

  /**
   * Returns a map of all compiled classes by source name.
   */
  public Map<String, CompiledClass> getClassFileMapBySource() {
    return exposedClassFileMapBySource;
  }

  /**
   * Returns an unmodifiable view of the set of compilation units, mapped by the
   * main type's qualified source name.
   */
  public Map<String, CompilationUnit> getCompilationUnitMap() {
    return exposedUnitMap;
  }

  /**
   * Returns an unmodifiable view of the set of compilation units.
   */
  public Collection<CompilationUnit> getCompilationUnits() {
    return exposedUnits;
  }

  public TypeOracle getTypeOracle() {
    return mediator.getTypeOracle();
  }

  private void assimilateUnits(TreeLogger logger,
      Collection<CompilationUnit> units) {
    for (CompilationUnit unit : units) {
      unitMap.put(unit.getTypeName(), unit);
      if (unit.isCompiled()) {
        for (CompiledClass compiledClass : unit.getCompiledClasses()) {
          classFileMap.put(compiledClass.getInternalName(), compiledClass);
          classFileMapBySource.put(compiledClass.getSourceName(), compiledClass);
        }
      }
    }
    mediator.addNewUnits(logger, units);
  }
}
