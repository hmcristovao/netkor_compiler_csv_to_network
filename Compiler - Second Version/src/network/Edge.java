package network;

public class Edge {

		private String start;
		private String end;
		private int count = 0;
		
		
		public Edge(String start, String end) {
			super();
			this.start = start;
			this.end = end;
			this.count = 1;
		}

		public boolean isEqual(String start, String end) {
			return this.start.equals(start) && this.end.equals(end);
		}
		
		public String getStart() {
			return start;
		}
		public void setStart(String start) {
			this.start = start;
		}
		public String getEnd() {
			return end;
		}
		public void setEnd(String end) {
			this.end = end;
		}
		public int getCount() {
			return count;
		}
		public void setCount(int count) {
			if(this.count >= 0) {
				this.count++;
			} else {
				this.count = count;
			}
		}
		
		
}
