/*
 * $Id: org.eclipse.jdt.ui.prefs 5004 2006-03-17 20:47:08 -0800 (Fri, 17 Mar 2006) eelco12 $
 * $Revision: 5004 $
 * $Date: 2006-03-17 20:47:08 -0800 (Fri, 17 Mar 2006) $
 * 
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package wicket.contrib.input.events.key;

public enum KeyType {
	a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z, Tab, Space, Return, Enter, Backspace, Scroll_lock, Caps_lock, Num_lock, Pause, Insert, Home, Delete, End, Page_up, Page_down, Left, Up, Right, Down, F1, F2, F3, F4, F5, F6, F7, F8, F9, F10, F11, F12;

	private final String keyCode;
/**
 * future thing if keyCode should be used.
 * @param keyCode
 */
	private KeyType(String keyCode) {
		this.keyCode = keyCode;
	}

	private KeyType() {
		keyCode = this.name();
	}

	public String getKeyCode() {
		return keyCode;
	}

}
