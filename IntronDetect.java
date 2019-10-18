import java.util.Stack;


public class IntronDetect 
{
	public IntronDetect(){}
	Postorder post = new Postorder();
	
	Node check_introns(Node x, String le, String re) 
	{
		if (x.parent != null) {
			if (le == "1") {
				if (x.data == "*") {
					x.right.parent = x.parent;
					if (x.parent.left == x)
						x.parent.left = x.right;
					else
						x.parent.right = x.right;
				}
				if (x.data == "^") {
					x.data = "1";
					x.left = null;
					x.right = null;
				}
			}
			if (re == "1") {
				if (x.data == "*") {
					x.left.parent = x.parent;
					if (x.parent.left == x)
						x.parent.left = x.left;
					else
						x.parent.right = x.left;
				}
				if (x.data == "^" || x.data == "/") {
					x.left.parent = x.parent;
					if (x.parent.left == x)
						x.parent.left = x.left;
					else
						x.parent.right = x.left;
				}

			}
			if (le == "0") {
				if (x.data == "+") {
					x.right.parent = x.parent;
					if (x.parent.left == x)
						x.parent.left = x.right;
					else
						x.parent.right = x.right;
				}
				if (x.data == "*" || x.data == "^") {
					x.data = "0";
					x.left = null;
					x.right = null;
				}
				if (x.data == "/") {
					x.data = "0";
					x.left = null;
					x.right = null;
				}
			}
			if (re == "0") {
				if (x.data == "+") {
					x.left.parent = x.parent;
					if (x.parent.left == x)
						x.parent.left = x.left;
					else
						x.parent.right = x.left;
				}
				if (x.data == "*") {
					x.data = "0";
					x.left = null;
					x.right = null;
				}
				if (x.data == "^") {
					x.data = "1";
					x.left = null;
					x.right = null;
				}
			}
		} else {
			if (le == "1") {
				if (x.data == "*") {
					x = x.right;
					x.parent.right = null;
					x.parent = null;

				}
				if (x.data == "^") {
					x.data = "1";
					x.left = null;
					x.right = null;
				}
			}
			if (re == "1") {
				if (x.data == "*" || x.data == "^" || x.data == "/") {
					x = x.left;
					x.parent.left = null;
					x.parent = null;

				}
			}
			if (le == "0") {
				if (x.data == "+") {
					x = x.right;
					x.parent.right = null;
					x.parent = null;

				}
				if (x.data == "*" || x.data == "^" || x.data == "/") {
					x.data = "0";
					x.left = null;
					x.right = null;
				}
			}
			if (re == "0") {
				if (x.data == "+") {
					x = x.left;
					x.parent.left = null;
					x.parent = null;
				}
				if (x.data == "*") {
					x.data = "0";
					x.right = null;
					x.left = null;
				}
				if (x.data == "^") {
					x.data = "1";
					x.left = null;
					x.right = null;
				}
			}
		}
		return x;
	}

	

	
	Node intron_detect(Node x) 
	{
		if (x == null)
			return null;

		intron_detect(x.left);
		intron_detect(x.right);

		Stack<String> st = new Stack<String>();
		String le, re;
		StringBuffer sb = new StringBuffer();

		if (x.data.equals("+") || x.data.equals("-") || x.data.equals("*")
				|| x.data.equals("/") || x.data.equals("^")
				|| x.data.equals("sin") || x.data.equals("cos")) {

			// Evaluate le and re expressions
			le = post.postorder(x.left, st);
			while (!st.empty())
				st.pop();
			re = post.postorder(x.right, st);

			sb.append(le);
			sb.append(x.data);
			sb.append(re);

			if (le.equals(re)) {
				if (x.data.equals("-")) {
					// Delete left and right subtrees
					x.left = null;
					x.right = null;
					x.data = "0";
				}
				if (x.data.equals("/")) {
					// Delete left and right subtrees
					x.left = null;
					x.right = null;
					x.data = "1";
				}
			} else {

				if (sb.toString() == le) {
					x.left.parent = x.parent;
					x.parent.left = x.left;
					x.parent = null;
				}
				if (sb.toString() == re) {
					x.right.parent = x.parent;
					x.parent.right = x.right;
					x.parent = null;
				}

			}
			x = check_introns(x, le, re);
		}

		return x;
	}

}
