import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * @author ambarmodi
 *
 */
public class HttpHandler implements Runnable {
	private Socket socket;
	private String res;
	
	public HttpHandler(Socket socket) {
		this.res = null;
		this.socket = socket;
	}

	public void run() {
		try {
			handleRequest();
		} catch (Exception e) {
			System.err.println("Error Occured: " + e.getMessage());
			try {
				socket.close();
				System.exit(0);
			} catch (IOException e1) {
				System.err.println("Error Closing socket Connection.\n" + e1.getMessage());
				System.exit(0);
			}
			System.err.println("Server is Terminating!");
		}
	}

	/**
	 * @throws Exception
	 */
	private void handleRequest() throws Exception {
		InputStream input;
		OutputStream output;
		String folderName = "www"; //folder name
		//
		File parent = new File(System.getProperty("user.dir")); //directory of executable file
		File root;
		if (!folderName.isEmpty())
			root = new File(parent.getAbsolutePath() + File.separator + folderName);
		else
			root = parent;
		//System.out.println(root.getAbsolutePath());

		if (root.exists()) {
			input = socket.getInputStream();
			output = socket.getOutputStream();
			serverRequest(input, output, root.toString());			
			output.close();
			input.close();
		} else {
			throw new Exception(root.getAbsolutePath() + " directory not present!\n");
		}
		socket.close();
	}

	/**
	 * @param input
	 * @param output
	 * @param root 
	 * @throws Exception
	 */
	private void serverRequest(InputStream input, OutputStream output, String root) throws Exception {
		String line;
		BufferedReader bf = new BufferedReader(new InputStreamReader(input));
		while ((line = bf.readLine()) != null) {
			if (line.length() <= 0) {
				break;
			}
			if (line.startsWith("GET")) {
				String filename= line.split(" ")[1].substring(1);
				File resource = new File(root + File.separator + filename);
				if (resource.isFile()) {
					res = filename;
					populateResponse(resource, output);
					Server.printResult(res, socket.getPort(), socket.getRemoteSocketAddress().toString());
				} else {
					String Content_NOT_FOUND = "<html><head></head><body><h1>File Not Found</h1></body></html>";
					
					String REQ_NOT_FOUND = "HTTP/1.1 404 Not Found\n\n";
					String header = REQ_NOT_FOUND+ Content_NOT_FOUND;
					
					output.write(header.getBytes());
				}
				break;
			}
		}
	}

	/**
	 * @param resource
	 * @param output
	 * @throws IOException
	 */
	private void populateResponse(File resource, OutputStream output) throws IOException {
		SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z");
		format.setTimeZone(TimeZone.getTimeZone("GMT"));

		String REQ_FOUND = "HTTP/1.0 200 OK\n";
		String SERVER = "Server: HTTP server/0.1\n";
		String DATE = "Date: " + format.format(new java.util.Date()) + "\n";
		String CONTENT_TYPE = "Content-type: " + URLConnection.guessContentTypeFromName(resource.getName());
		String LENGTH = "Content-Length: " + (resource.length()) + "\n\n";

		String header = REQ_FOUND + SERVER + DATE + CONTENT_TYPE + LENGTH;
		output.write(header.getBytes());
		
		Files.copy(Paths.get(resource.toString()), output);
		output.flush();
	}
}
