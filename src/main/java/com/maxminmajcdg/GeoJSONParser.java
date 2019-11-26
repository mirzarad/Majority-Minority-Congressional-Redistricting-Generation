package com.maxminmajcdg;

import java.io.ByteArrayOutputStream; 
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.geotools.geojson.geom.GeometryJSON;
import org.json.JSONArray;
import org.json.JSONObject;
import org.locationtech.jts.geom.Geometry;


import com.maxminmajcdg.entities.GeometryEntity;
import com.maxminmajcdg.entities.MultiPolygonEntity;
import com.maxminmajcdg.entities.PolygonEntity;

public class GeoJSONParser {
	
	public static GeometryEntity getGeometryEntity(Geometry shapeFile) {
		String type = shapeFile.getGeometryType();
		GeometryJSON geoJSON = new GeometryJSON();
		OutputStream result = new ByteArrayOutputStream();
		try {
			geoJSON.write(shapeFile, result);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		JSONObject json = new JSONObject(result.toString());

		switch(type) {
		case "Polygon":
			List<List<double[]>> polyCoords = GeoJSONParser.getPolyArray((JSONArray)json.get("coordinates"));
			PolygonEntity poly = new PolygonEntity();
			poly.setCoordinates(polyCoords);
			return poly;
		case "MultiPolygon":
			List<List<List<double[]>>>  multiPolyCoords =GeoJSONParser.getMultiPolyArray((JSONArray)json.get("coordinates"));
			MultiPolygonEntity multiPoly = new MultiPolygonEntity();
			multiPoly.setCoordinates(multiPolyCoords);
			return multiPoly;
			default:
				return null;
		}
	}
	
	public static List<List<double[]>> getPolyArray(JSONArray jsonArray) {
		List<List<double[]>> result = new ArrayList<List<double[]>>();
		
		for(Object j : jsonArray) {
			ArrayList<double[]> inner = new ArrayList<double[]>();	
			for (Object k : (JSONArray) j) {
				JSONArray l = (JSONArray) k;
				inner.add(new double[] {l.getDouble(0), l.getDouble(1)});
			}
			result.add(inner);
		}
		
		return result;
	}
	
	public static List<List<List<double[]>>> getMultiPolyArray(JSONArray jsonArray) {
		List<List<List<double[]>>> result = new ArrayList<List<List<double[]>>>();
		
		for(Object j : jsonArray) {
			List<List<double[]>> a = new ArrayList<List<double[]>>();	
			for (Object k : (JSONArray) j) {
				ArrayList<double[]> b = new ArrayList<double[]>();
				for (Object l : (JSONArray) k) {
					JSONArray o = (JSONArray) l;
					b.add(new double[] {o.getDouble(0), o.getDouble(1)});
				}
				a.add(b);
			}
			result.add(a);
		}
		
		return result;
	}
}
