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
 *    Franz Wilhelmstötter (franz.wilhelmstoetter@gmx.at)
 */
package org.jenetics;

import org.jenetics.internal.collection.ArrayProxy;
import org.jenetics.internal.collection.ArrayProxyISeq;
import org.jenetics.internal.collection.ArrayProxyMSeq;

import org.jenetics.util.bit;

/**
 * @author <a href="mailto:franz.wilhelmstoetter@gmx.at">Franz Wilhelmstötter</a>
 * @since 1.4
 * @version 3.0 &mdash; <em>$Date: 2014-04-20 $</em>
 */
final class BitGeneArray extends ArrayProxyMSeq<BitGene> {

	BitGeneArray(final Proxy proxy) {
		super(proxy);
	}

	BitGeneArray(final byte[] array, final int start, final int end) {
		this(new Proxy(array, start, end));
	}

	@Override
	public BitGeneArray copy() {
		return new BitGeneArray(((Proxy)_proxy).copy());
	}

	@Override
	public BitGeneISeq toISeq() {
		return new BitGeneISeq((Proxy)_proxy.seal());
	}

	/**
	 * @author <a href="mailto:franz.wilhelmstoetter@gmx.at">Franz Wilhelmstötter</a>
	 * @since 1.4
	 * @version 1.4 &mdash; <em>$Date: 2014-04-20 $</em>
	 */
	static final class BitGeneISeq extends ArrayProxyISeq<BitGene> {
		private static final long serialVersionUID = 1L;

		public BitGeneISeq(final Proxy proxy) {
			super(proxy);
		}

		void copyTo(final byte[] array) {
			final Proxy proxy = (Proxy)_proxy;
			System.arraycopy(proxy._array, 0, array, 0, proxy._array.length);
		}

		@Override
		public BitGeneArray copy() {
			return new BitGeneArray(((Proxy)_proxy).copy());
		}

	}

	/**
	 * @author <a href="mailto:franz.wilhelmstoetter@gmx.at">Franz Wilhelmstötter</a>
	 * @since 1.4
	 * @version 3.0 &mdash; <em>$Date: 2014-04-20 $</em>
	 */
	static final class Proxy extends ArrayProxy<BitGene, byte[], Proxy> {
		private static final long serialVersionUID = 1L;

		Proxy(final byte[] array, final int start, final int end) {
			super(array, start, end, Proxy::new, b -> b.clone());
		}

		Proxy(final int length) {
			this(bit.newArray(length), 0, length);
		}

		@Override
		public BitGene __get(final int absoluteIndex) {
			return BitGene.of(bit.get(_array, absoluteIndex));
		}

		@Override
		public void __set(
			final int absoluteIndex,
			final BitGene value
		) {
			bit.set(_array, absoluteIndex, value.booleanValue());
		}

		@Override
		public void swap(
			final int start, final int end,
			final ArrayProxy<BitGene, ?, ?> other, final int otherStart
		) {
			if (other instanceof Proxy) {
				swap(start, end, (Proxy)other, otherStart);
			} else {
				super.swap(start, end, other, otherStart);
			}
		}

		private void swap(
			final int start, final int end,
			final Proxy other, final int otherStart
		) {
			checkIndex(start, end);
			other.checkIndex(otherStart, otherStart + (end - start));
			cloneIfSealed();
			other.cloneIfSealed();

			bit.swap(
				_array, start + _start, end + _start,
				other._array, otherStart + other._start
			);
		}

		@Override
		public Proxy copy() {
			final Proxy proxy = new Proxy(_length);
			if (_start == 0 && _end == _length) {
				proxy._array = _array.clone();
			} else if (_start == 0) {
				System.arraycopy(
					_array, 0, proxy._array, 0, proxy._array.length
				);
			} else {
				for (int i = _length; --i >= 0;) {
					bit.set(proxy._array, i, bit.get(_array, i + _start));
				}
			}
			return proxy;
		}

	}

}
