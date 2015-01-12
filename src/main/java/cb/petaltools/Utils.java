/**
 * Copyright (c) 2001 Markus Dahm
 * Copyright (C) 2015 BITPlan GmbH
 *

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
   * @return the pathmap created
   */
	public static Map<String,String> createPathMap(String[] args) {
		return createPathMap(args, 1);
	}
	
	/**
	 * create a path map from the given arguments
	 * @param args e.g. $A=APath $B=BPath
	 * @param startingArg - where to start e.g. 0
	 * @return - a map $A->APath, $B->BPath
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
