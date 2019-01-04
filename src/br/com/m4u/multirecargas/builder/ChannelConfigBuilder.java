package br.com.m4u.multirecargas.builder;

import br.com.m4u.multirecargas.entity.Channel;
import br.com.m4u.multirecargas.entity.ChannelValue;

import java.io.*;
import java.util.*;

public class ChannelConfigBuilder implements Serializable {

    private Map<String, Channel> channels = new HashMap<String, Channel>();

    public void build(String configFilePath) {
        BufferedReader br = null;
        String fileLine = "";
        String csvDivisor = ",";
        try {
            final List<ChannelValue> lines = new ArrayList<ChannelValue>();
            br = new BufferedReader(new FileReader(configFilePath));
            Boolean firstLine = Boolean.TRUE;
            while ((fileLine = br.readLine()) != null) {
                if(!firstLine) {
                    final String[] parts = fileLine.split(csvDivisor);
                    System.out.println("Lendo " + parts[1] + "-" + parts[2]);
                    lines.add(new ChannelValue(parts[1],
                            Integer.valueOf(parts[2]) * 100,
                            Integer.valueOf(parts[3]) * 100,
                            Integer.valueOf(parts[4])));

                }
                firstLine = Boolean.FALSE;
            }
            buildMap(lines);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            this.channels = null;
        } catch (IOException ex) {
            ex.printStackTrace();
            this.channels = null;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Map<String, Channel> getChannels() {
        return this.channels;
    }

    private void buildMap(final List<ChannelValue> channelValueList) {
        final List<String> listNames = buildChannelNameList();
        for (final String channelName: listNames) {
            this.channels.put(channelName, new Channel(channelName, channelValueList));
        }
    }

    private List<String> buildChannelNameList() {
        final String names = "ANDROID,APP_MINHA_CLARO,CLARO_PROG_NUANCE,CLARO_SMS_555,CLARO_URA_1052,"  +
                "CLARO_URA_555,CLARO_URA_INT,CLARO_URA_ONE,CLARO_USSD_1052,CLARO_USSD_544,CLARO_USSD_555," +
                "CLARO_USSD_ONE,CLARO_USSD_WIB,CLARO_WAP_NUANCE,CLARO_WEB_NUANCE";
        return Arrays.asList(names.split(","));
    }
}
