/*
 * � Copyright IBM Corp. 2012-2013
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at:
 * 
 * http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or 
 * implied. See the License for the specific language governing 
 * permissions and limitations under the License.
 */
package com.ibm.commons.log;

import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;

import com.ibm.commons.Platform;


/**
 * Console handler that uses a custom formatter. 
 * @ibm-not-published
 */
public class BasicConsoleHandler extends StreamHandler {
	
	
	private static class ConsoleOutputStream extends OutputStream {

		@Override
		public void flush() throws IOException {
			Platform.getInstance().getOutputStream().flush();
		}

		@Override
		public void write(byte[] b, int off, int len) throws IOException {
			Platform.getInstance().getOutputStream().write(b, off, len);
		}

		@Override
		public void write(byte[] b) throws IOException {
			Platform.getInstance().getOutputStream().write(b);
		}

		@Override
		public void write(int b) throws IOException {
			Platform.getInstance().getOutputStream().write(b);
		}
		
	}
	
	public BasicConsoleHandler(boolean dateTime) {
		setOutputStream(new ConsoleOutputStream());
		setFormatter(new BasicFormatter(dateTime));
	}
	
	public BasicConsoleHandler() {
		this(true);
	}

	@Override
	public synchronized void close() throws SecurityException {
		flush();
	}

	@Override
	public synchronized void publish(LogRecord record) {
		super.publish(record);
		flush();
	}	
}
