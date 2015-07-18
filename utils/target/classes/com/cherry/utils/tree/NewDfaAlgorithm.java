package com.cherry.utils.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * @author  新版本 去掉了 HashMap 减少了内存但是 不好重新构建
 *
 */
public class NewDfaAlgorithm {
	private Logger logger = LoggerFactory.getLogger(NewDfaAlgorithm.class);
	public DFAState m_StartState;//起始状态
	
	public NewDfaAlgorithm() {//初始化DFA，设置初始状态 
		m_StartState = new DFAState(null);
		m_StartState.m_Failure = m_StartState;
	}
	
	protected void cleanStates(DFAState state) {
		for (char key : state.m_Goto.keySet()) {
			cleanStates(state.m_Goto.get(key));
		}
		state = null;
	}
	
	public void beforeInit() {
		cleanStates(m_StartState); 
		m_StartState = new DFAState(null);
		m_StartState.m_Failure = m_StartState;
	}
	
	protected void doFailure() {
		LinkedList<DFAState> q = new LinkedList<DFAState>();
		// 首先设置起始状态下的所有子状态,设置他们的m_Failure为起始状态，并将他们添加到q中
		for (char c : m_StartState.m_Goto.keySet()) {
			q.add(m_StartState.m_Goto.get(c));
			m_StartState.m_Goto.get(c).m_Failure = m_StartState;
		}

		while (!q.isEmpty()) {
			// 获得q的第一个element，并获取它的子节点，为每个子节点设置失败跳转的状态节点
			DFAState r = q.getFirst();
			DFAState state;
			q.remove();
			for (char c : r.m_Goto.keySet()) {
				q.add(r.m_Goto.get(c));
				// 从父节点的m_Failure(m1)开始，查找包含字符c对应子节点的节点，
				// 如果m1找不到，则到m1的m_Failure查找，依次类推
				state = r.m_Failure;
				while (state.m_Goto.containsKey(c) == false) {
					state = state.m_Failure;
					if (state == m_StartState) {
						break;
					}
				}
				// 如果找到了，设置该子节点的m_Failure为找到的目标节点(m2)，
				// 并把m2对应的关键词列表添加到该子节点中
				if (state.m_Goto.containsKey(c)) {
					r.m_Goto.get(c).m_Failure = state.m_Goto.get(c);
					for (String str : r.m_Goto.get(c).m_Failure.m_Output) {
						r.m_Goto.get(c).m_Output.add(str);
					}
				} else { //找不到，设置该子节点的m_Failure为初始节点
					r.m_Goto.get(c).m_Failure = m_StartState;
				}
			}
		}
	}
	
	public void endInit() {
		doFailure();
	}
	
	public void doAddWord(String keyword , String value) {
		int i;
		DFAState curState = m_StartState;
		for (i = 0; i < keyword.length(); i++) {
			curState = curState.AddGoto(keyword.charAt(i));
		}
		curState.m_Output.add(keyword);
		curState.code = value ; 
	}
	
	// 检索字符串text是否包含关键词，并调用outputCallback处理匹配的关键词
	public void search(String text, DFAOutCallBack outputCallback) {
			 DFAState curState = m_StartState;
			 int i;
			 for (i = 0; i < text.length(); ++i) {
				 // 查看状态机中当前状态下该字符对应的下一状态，如果在当前状态下找不到满足该个字符的状态路线，
				 // 则返回到当前状态的失败状态下继续寻找，直到初始状态
				 
				 
				 char textChar = text.charAt(i);
				 
				 while (curState.m_Goto.containsKey(textChar) == false) {
					 if (curState.m_Failure != m_StartState) {
						 if (curState == curState.m_Failure) { //陷入死循环了...
							 logger.error("DFA Failure");
							 break;
						 }
						 curState = curState.m_Failure; // 返回到当前状态的失败状态
					 } else {
						 curState = m_StartState;
						 break;
					 }
				 }
				 // 如果当前状态下能找到该字符对应的下一状态，则跳到下一状态m，
				 // 如果状态m包含了m_Output，表示匹配到了关键词，具体原因请继续往下看
				 if (curState.m_Goto.containsKey(textChar)) {
					 curState = curState.m_Goto.get(textChar);
				     if (!curState.m_Output.isEmpty()) {
				    	 curState.Output(outputCallback);
				    	 return ;
				     }
				 }
			 }
		 }
	
	
	static char[] chars = { '0' , '1' , '2' , '3' , '4' , '5' , '6' , '7' , '8' , '9' } ;
	
	public static char getChar( char c ) {
		int index = ( (int)c ) - 48 ;
		if( index >= 0 && index < 10 ) {
			return chars[ index ] ;
		}
		return c ;
	}
	
	
	private class DFAState{
		// 记录了该状态节点下，字符-->另一个状态的对应关系
		public Map<Character, DFAState> m_Goto = new HashMap<Character, DFAState>();
		// 如果该状态下某具体字符找不到对应的下一状态，应该跳转到m_Failure状态继续查找
		public DFAState m_Failure;
		// 该状态节点的前一个节点
		public DFAState m_Parent;
		
		// 记录了到达该节点时，匹配到的关键词
		public List<String> m_Output = new ArrayList<String>();
		
		public String code ;
		
		public DFAState(DFAState parent){
			this.m_Parent = parent;
			this.m_Failure = null;
		}
		
		// 为当前状态节点添加字符c对应的下一状态
		DFAState AddGoto(char c) {
			char cc = getChar(c) ;
			if (!m_Goto.containsKey(cc)) {
				// not in the goto table
		        DFAState newState = new DFAState(this);
		        m_Goto.put(cc, newState);
		        return newState;
	        } else {
	        	return m_Goto.get(getChar(cc) ); 
	        }
		}
		
		// 调用outputCallback处理当前状态节点对应的关键词列表
		void Output(DFAOutCallBack outputCallback) {
			for (Iterator iter = m_Output.iterator(); iter.hasNext();) {
				String element = (String) iter.next();
				outputCallback.CallBack(element , this.code );
			}
		}
	}
	
	public static void main(String[] args) {
		NewDfaAlgorithm dfaAlgorithm = new NewDfaAlgorithm() ;
		dfaAlgorithm.beforeInit() ;
		dfaAlgorithm.doAddWord("江泽民", "王八") ;
		dfaAlgorithm.doAddWord("李鹏", "王八") ;
		dfaAlgorithm.doAddWord("李小鹏", "王八蛋") ;
		dfaAlgorithm.endInit() ;
		
		/*dfaAlgorithm.search("我了个操江泽民买皮", new DFAOutCallBack() {
			
			@Override
			public void CallBack(String keyword, String value) {
				System.out.println(keyword + " | " + value );
			}
		}) ;
		
		dfaAlgorithm.search("江泽", new DFAOutCallBack() {
			
			@Override
			public void CallBack(String keyword, String value) {
				System.out.println(keyword + "  " + value );
			}
		}) ;*/
		
		dfaAlgorithm.search("泽民", new DFAOutCallBack() {
			
			@Override
			public void CallBack(String keyword, String value) {
				System.out.println(keyword + "  " + value );
			}
		}) ;
		
		/*dfaAlgorithm.search("胡锦涛", new DFAOutCallBack() {
			
			@Override
			public void CallBack(String keyword, String value) {
				System.out.println(keyword + "  " + value );
			}
		}) ;
		
		dfaAlgorithm.search("李小鹏", new DFAOutCallBack() {
			
			@Override
			public void CallBack(String keyword, String value) {
				System.out.println(keyword + "  " + value );
			}
		}) ;*/
		
		
	}
	
	
}
