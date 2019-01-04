package br.com.m4u.multirecargas.builder;

import br.com.m4u.multirecargas.entity.Channel;
import br.com.m4u.multirecargas.entity.ChannelValue;
import br.com.m4u.multirecargas.entity.Line;

import java.io.*;
import java.util.*;

public class ChannelConfigBuilder implements Serializable {

    private Map<String, Channel> channels;

    public ChannelConfigBuilder build(String cbannelName, String configFilePath) {

        BufferedReader br = null;
        String fileLine = "";
        String csvDivisor = ",";
        try {
            final List<Line> lines = new ArrayList<Line>();
            br = new BufferedReader(new FileReader(configFilePath));
            while ((fileLine = br.readLine()) != null) {
                final String[] parts = fileLine.split(csvDivisor);
                lines.add(new Line(parts[1], Integer.valueOf(parts[2]),
                        Integer.valueOf(parts[3]), Integer.valueOf(parts[4])));
            }
            buildMap(lines);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return this;
    }

    public Map<String, Channel> getChannels() {
        return this.channels;
    }

    private void buildMap(final List<Line> lineList) {
        final String names = "ANDROID,APP_MINHA_CLARO,CLARO_PROG_NUANCE,CLARO_SMS_555,CLARO_URA_1052,"  +
                "CLARO_URA_555,CLARO_URA_INT,CLARO_URA_ONE,CLARO_USSD_1052,CLARO_USSD_544,CLARO_USSD_555," +
                "CLARO_USSD_ONE,CLARO_USSD_WIB,CLARO_WAP_NUANCE,CLARO_WEB_NUANCE";
        final List<String> listNames = Arrays.asList(names.split(","));
        channels = new HashMap<String, Channel>();
        for (final String channelName: listNames) {
            final List<ChannelValue> channelValueList = new ArrayList<>();
            for (final Line line : lineList) {
                channelValueList.add(new ChannelValue(line.getDdd(), line.getValue() * 100, line.getBonusValue() * 100, line.getDaysOfValidity()));
            }
            this.channels.put(channelName, new Channel(channelName, channelValueList));
        }
    }
 }
