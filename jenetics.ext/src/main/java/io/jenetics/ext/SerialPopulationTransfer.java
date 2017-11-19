/*
 * Java Genetic Algorithm Library (@__identifier__@).
 * Copyright (c) @__year__@ Franz Wilhelmstötter
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Author:
 *    Franz Wilhelmstötter (franz.wilhelmstoetter@gmail.com)
 */
package io.jenetics.ext;

import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import io.jenetics.Gene;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.engine.EvolutionStart;

/**
 * @author <a href="mailto:franz.wilhelmstoetter@gmail.com">Franz Wilhelmstötter</a>
 * @version !__version__!
 * @since !__version__!
 */
public class SerialPopulationTransfer<
	G extends Gene<?, G>,
	C extends Comparable<? super C>
>
	implements
		Supplier<EvolutionStart<G, C>>,
		UnaryOperator<EvolutionResult<G, C>>,
		Consumer<EvolutionResult<G, C>>
{

	public SerialPopulationTransfer() {
	}

	@Override
	public EvolutionStart<G, C> get() {
		return null;
	}

	@Override
	public EvolutionResult<G, C> apply(EvolutionResult<G, C> gcEvolutionResult) {
		return null;
	}

	@Override
	public void accept(EvolutionResult<G, C> gcEvolutionResult) {
	}
}