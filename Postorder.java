import java.util.Stack;


public class Postorder 
{
	public Postorder(){}
	
	public String postorder(Node x, Stack<String> st) 
	{
		StringBuffer sv = new StringBuffer();
		if (x != null) {

			postorder(x.left, st);
			postorder(x.right, st);
			if (x.data == "+" || x.data == "-" || x.data == "*"
					|| x.data == "/" || x.data == "^") {
				String op = st.peek();
				String Val1 = st.peek();
				st.pop();
				String Val2 = st.peek();
				st.pop();

				sv.append(Val1);
				sv.append(op);
				sv.append(Val2);
				st.push(sv.toString());
			} else if (x.data.equals("sin") || x.data.equals("cos")) {
				String op = st.peek();
				st.pop();

				sv.append(op);
				if (x.data.equals("sin"))
					sv.append("sin");
				if (x.data.equals("cos"))
					sv.append("cos");
				st.push(sv.toString());

			} else {
				st.push(x.data);
			}
		} else {
			return null;
		}
		return st.peek();

	}

	
	
}
