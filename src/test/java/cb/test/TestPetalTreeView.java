/**
 * Copyright (c) 2001 Markus Dahm
 * Copyright (C) 2015 BITPlan GmbH
 *
 * Pater-Delp-Str. 1
 * D-47877 Willich-Schiefbahn
 *
 * http://www.bitplan.com
 * 
 * This source is part of
 * https://github.com/BITPlan/CrazyBeans
 * and the license as outlined there applies
 * 
 */
package cb.test;

import java.awt.GraphicsEnvironment;
import java.util.logging.Level;

import org.junit.Test;

import cb.petaltools.PetalTreeView;

/**
 * Display petal file visually. Property names are displayed as tool tips.
 *
 * @version $Id: Test3.java,v 1.3 2001/11/01 15:56:49 dahm Exp $
 * @author <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class TestPetalTreeView extends BaseTest {

	public static final int viewPause=300; // how many millisecs to wait
	
	@Test
	public void testPetalTreeView()  {
		
		if (!GraphicsEnvironment.isHeadless()) {

			for (String exampleModelFileName : exampleModelFileNames) {
				String exampleModelFilePath = super
						.getExampleFileName(exampleModelFileName);
				try {
					PetalTreeView petalTreeView=new PetalTreeView();
					petalTreeView.showTree(exampleModelFilePath);
					Thread.sleep(viewPause);
				} catch (Exception e) {
					LOGGER.log(Level.WARNING,exampleModelFilePath+":"+e.getMessage());
				}
				
			}
		}
	}

}
