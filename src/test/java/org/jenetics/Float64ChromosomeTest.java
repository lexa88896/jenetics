/*
 * Java Genetic Algorithm Library (@!identifier!@).
 * Copyright (c) @!year!@ Franz Wilhelmstötter
 *  
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * Author:
 *     Franz Wilhelmstötter (franz.wilhelmstoetter@gmx.at)
 *     
 */
package org.jenetics;

import static org.jenetics.util.Accumulators.accumulate;
import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.Random;

import javolution.context.LocalContext;
import javolution.xml.stream.XMLStreamException;

import org.jenetics.Distribution.Uniform;
import org.jenetics.util.Accumulators.MinMax;
import org.jenetics.util.RandomRegistry;
import org.jenetics.util.Accumulators.Variance;
import org.jscience.mathematics.number.Float64;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:franz.wilhelmstoetter@gmx.at">Franz Wilhelmstötter</a>
 * @version $Id$
 */
public class Float64ChromosomeTest {

	@Test(invocationCount = 10)
    public void newInstance() {
		LocalContext.enter();
		try {
			RandomRegistry.setRandom(new Random());
			
			final Float64Chromosome chromosome = new Float64Chromosome(0, 100, 1000);
			
			final MinMax<Float64> mm = new MinMax<Float64>();
			accumulate(chromosome, mm.adapt(Float64Gene.VALUE));
			
			Assert.assertTrue(mm.getMin().compareTo(0) >= 0);
			Assert.assertTrue(mm.getMax().compareTo(100) <= 100);
			
			final Variance<Float64> variance = new Variance<Float64>();
			accumulate(chromosome, variance.adapt(Float64Gene.VALUE));
			
			Assert.assertEquals(
					variance.getMean(), 
					Uniform.mean(0, 100), 
					2*variance.getStandardError()
				);
			Assert.assertEquals(
					variance.getVariance(), 
					Uniform.variance(0, 100), 
					30
				);
		} finally {
			LocalContext.exit();
		}
    }

    @Test
    public void testCreate() {
        Float64Chromosome c1 = new Float64Chromosome(-12.0, 230.123, 100);
        Float64Chromosome c2 = c1.newInstance();
        
        for (NumberGene<Float64, ?> g : c2) {
            assertEquals(-12.0, g.getMin().doubleValue());
            assertEquals(230.123, g.getMax().doubleValue());
        }
    }
    
    @Test
    public void equals() {
    	Float64Chromosome c1 = new Float64Chromosome(-12.0, 230.123, 3);
    	Float64Chromosome c2 = new Float64Chromosome(-12.0, 230.123, 3);
    	Assert.assertFalse(c1.equals(c2));
    	
    	
    	
    	c2 = new Float64Chromosome(c1.toArray());
    	Assert.assertNotSame(c2, c1);
    	Assert.assertEquals(c2, c1);
    	
    	c2 = new Float64Chromosome(c1.toArray().copy());
    	Assert.assertNotSame(c2, c1);
    	Assert.assertEquals(c2, c1);
    }
    
    @Test
    public void xmlSerialize() throws XMLStreamException {
    	SerializeUtils.testXMLSerialization(new Float64Chromosome(-12.0, 230.123, 1));
    }
    
    @Test
    public void objectSerialize() throws IOException {
    	SerializeUtils.testSerialization(new Float64Chromosome(-12.0, 230.123, 1));
    }

}
