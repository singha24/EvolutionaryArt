package controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataFormatImpl;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
public class ChangeImageResolution {
	private static String getFileExtension(File file) {
		String fileName = file.getName();
		int lastDot = fileName.lastIndexOf('.');
		return fileName.substring(lastDot + 1);
	}

	public static BufferedImage readImage(File file) throws IOException {
		ImageInputStream stream = null;
		BufferedImage image = null;
		try {
			stream = ImageIO.createImageInputStream(file);
			Iterator<ImageReader> readers = ImageIO.getImageReaders(stream);
			if (readers.hasNext()) {
				ImageReader reader = readers.next();
				reader.setInput(stream);
				image = reader.read(0);
			}
		} finally {
			if (stream != null) {
				stream.close();
			}
		}

		return image;
	}

	public static IIOMetadataNode createResolutionMetadata(double resolutionDPI) {
		String pixelSize = Double.toString(25.4 / resolutionDPI);

		IIOMetadataNode horizontal = new IIOMetadataNode("HorizontalPixelSize");
		horizontal.setAttribute("value", pixelSize);

		IIOMetadataNode vertical = new IIOMetadataNode("VerticalPixelSize");
		vertical.setAttribute("value", pixelSize);

		IIOMetadataNode dimension = new IIOMetadataNode("Dimension");
		dimension.appendChild(horizontal);
		dimension.appendChild(vertical);

		IIOMetadataNode root = new IIOMetadataNode(
				IIOMetadataFormatImpl.standardMetadataFormatName);
		root.appendChild(dimension);

		return root;
	}

	public static void writeImage(File outputFile, BufferedImage image,
			IIOMetadataNode newMetadata) throws IOException {
		String extension = getFileExtension(outputFile);
		if (!(extension.toLowerCase().equals("png"))) { // a bug which means png
														// files throw an error.
			ImageTypeSpecifier imageType = ImageTypeSpecifier
					.createFromBufferedImageType(image.getType());

			ImageOutputStream stream = null;
			try {
				Iterator<ImageWriter> writers = ImageIO
						.getImageWritersBySuffix(extension);
				while (writers.hasNext()) {
					ImageWriter writer = writers.next();
					ImageWriteParam writeParam = writer.getDefaultWriteParam();
					IIOMetadata imageMetadata = writer.getDefaultImageMetadata(
							imageType, writeParam);
					if (!imageMetadata.isStandardMetadataFormatSupported()) {
						continue;
					}
					if (imageMetadata.isReadOnly()) {
						continue;
					}
					imageMetadata.mergeTree(
							IIOMetadataFormatImpl.standardMetadataFormatName,
							newMetadata);

					IIOImage imageWithMetadata = new IIOImage(image, null,
							imageMetadata);

					stream = ImageIO.createImageOutputStream(outputFile);
					writer.setOutput(stream);
					writer.write(null, imageWithMetadata, writeParam);
				}
			} finally {
				if (stream != null) {
					stream.close();
					//writeMime(outputFile);
				}
			}
		}
	}
	
	public static Path toPath(File output){
		return FileSystems.getDefault().getPath(output.getPath()); 
	}

	public static void writeMime(File outputfile) {

		Path file = toPath(outputfile);
		UserDefinedFileAttributeView view = Files.getFileAttributeView(file,
				UserDefinedFileAttributeView.class);
		
		try {
			view.write("TEEEESSSSSSTTTT", Charset.defaultCharset().encode("text/html"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public static byte[] writeCustomData(BufferedImage buffImg, String key, String value) throws Exception {
	    ImageWriter writer = ImageIO.getImageWritersByFormatName("png").next();

	    ImageWriteParam writeParam = writer.getDefaultWriteParam();
	    ImageTypeSpecifier typeSpecifier = ImageTypeSpecifier.createFromBufferedImageType(BufferedImage.TYPE_INT_RGB);

	    //adding metadata
	    IIOMetadata metadata = writer.getDefaultImageMetadata(typeSpecifier, writeParam);

	    IIOMetadataNode textEntry = new IIOMetadataNode("tEXtEntry");
	    textEntry.setAttribute("keyword", key);
	    textEntry.setAttribute("value", value);

	    IIOMetadataNode text = new IIOMetadataNode("tEXt");
	    text.appendChild(textEntry);

	    IIOMetadataNode root = new IIOMetadataNode("javax_imageio_png_1.0");
	    root.appendChild(text);

	    metadata.mergeTree("javax_imageio_png_1.0", root);

	    //writing the data
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    ImageOutputStream stream = ImageIO.createImageOutputStream(baos);
	    writer.setOutput(stream);
	    writer.write(metadata, new IIOImage(buffImg, null, metadata), writeParam);
	    stream.close();

	    return baos.toByteArray();
	}
	

	public void readPNG(File outputfile){
		/*PngReader pngr = FileHelper.createPngReader(outputfile);
		pngr.readSkippingAllRows();
		for (PngChunk c : pngr.getChunksList().getChunks()) {
		      if (!ChunkHelper.isText(c))   continue;
		      PngChunkTextVar ct = (PngChunkTextVar) c;
		      String key = ct.getKey();
		      String val = ct.getVal();
		      // ... 
		}*/
	}

	/*
	 * public static void main(String[] args) { if (args.length != 3) {
	 * System.out
	 * .println("Usage: ChangeImageResolution inputFile newResolutionDPI outputFile"
	 * ); return; }
	 * 
	 * try { File inputFile = new File(args[0]); double resolutionDPI =
	 * Double.parseDouble(args[1]); File outputFile = new File(args[2]);
	 * 
	 * BufferedImage image = readImage(inputFile); IIOMetadataNode newMetadata =
	 * createResolutionMetadata(resolutionDPI); writeImage(outputFile, image,
	 * newMetadata); } catch (Exception e) { e.printStackTrace(); } }
	 */
}