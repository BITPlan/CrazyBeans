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
 * @since Nov 18, 2004
 */
package cb.petaltools;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author moroff
 *
 */
public class Utils {

  /**
   * create a PathMap for the given args
   * @param args
   * @return
   */
	public static Map<String,String> createPathMap(String[] args) {
		return createPathMap(args, 1);
	}
	
	/**
	 * @param args
	 * @return
	 */
	public static Map<String,String> createPathMap(String[] args, int startingArg) {
		Map<String,String> pathMap = new HashMap<String,String>();
	    Pattern pattern = Pattern.compile("(\\$\\w+)=(.+)");
	    for ( int i=startingArg; i<args.length; ++i) {
	    	Matcher matcher = pattern.matcher(args[i]);
	    	if ( matcher.find() ) {
		    	String key = matcher.group(1);
		    	String path = matcher.group(2);
		    	pathMap.put(key,path);
	    	}
	    }
		return pathMap;
	}
}
