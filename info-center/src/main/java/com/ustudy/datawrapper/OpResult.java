package com.ustudy.datawrapper;

public class OpResult {
	
	public enum OpStatus {
		OP_Successful (200),               // 200
		OP_Created (201),                  // 201
		OP_NoContent (204),                // 204
		OP_BadRequest (400),               // 400
		OP_NotFound (404),                 // 404
		Op_InterServerError (500),         // 500
		Op_ServiceUnavailable (503);       // 503
		
		private int val;
		
		private OpStatus(final int val) {
			this.val = val;
		}
				
		public int getVal() {
			return this.val;
		}
	}

	private OpStatus status;
	
	// json data for SQL statement result
	private String data;
	
	// auto increment key for insert operation
	private int autoid = -1;
	
	public OpResult(OpStatus st, String res) {
		status = st;
		data = res;
	}
	
	public void setStatus (OpStatus st) {
		status = st;
	}
	
	public OpStatus getStatus () {
		return status;
	}
	
	public void setData (String res) {
		data = res;
	}
	
	public String getData () {
		return data;
	}
	
	public void setKey(int val) {
		autoid = val;
	}
	
	public int getKey() {
		return autoid;
	}
}
