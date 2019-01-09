package br.com.m4u.multirecargas.service;

import br.com.m4u.multirecargas.entity.Channel;
import br.com.m4u.multirecargas.entity.ChannelValue;
import br.com.m4u.multirecargas.entity.Product;
import br.com.m4u.multirecargas.entity.ProductValue;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComparisonService implements Serializable {

    public Integer init(final Map<String, Channel> channelMap, final String fileName, final String outputPath) {
        if(channelMap != null) {
            final Map<String, Product> productMap = loadFile(fileName);
            compare(channelMap, productMap, outputPath);
        } else {
            System.out.println("Ocorreu um erro ao tentar carregar o arquivo de configuracao.");
        }
        return 3;
    }

    private void compare(final Map<String, Channel> channelMap, final Map<String, Product> productMap, final String outputPath) {
        final List<String> channelsNotFoundList = new ArrayList<>();
        final List<ChannelValue> productsNotFoundList = new ArrayList<>();

        for(Map.Entry<String, Channel> channelEntry : channelMap.entrySet()) {
            final Product product = productMap.get(channelEntry.getKey());
            if (product != null) {
                for (final ChannelValue channelValue : channelEntry.getValue().getValues()) {
                    if (!findProduct(product.getValues(), channelValue)) {
                        productsNotFoundList.add(channelValue);
                    }
                }
            } else {
                channelsNotFoundList.add(channelEntry.getKey());
            }
        }

        handleChannelNotFound(outputPath, channelsNotFoundList);
        handleProductNotFound(outputPath, productsNotFoundList);
    }

    private Boolean findProduct(final List<ProductValue> productValuesList, final ChannelValue channelValue) {
        for (final ProductValue value : productValuesList) {
            if (value.getDdd().equals(channelValue.getDdd()) && value.getAmount().equals(channelValue.getValue())) {
                return true;
            }
        }
        return false;
    }

    private void handleProductNotFound(final String outputPath, final List<ChannelValue> productList) {
        if(productList != null && !productList.isEmpty()) {
            try {
                final String fileName = outputPath  + "/products_not_found.txt";
                final FileWriter resultFile = new FileWriter(fileName);
                final PrintWriter writer = new PrintWriter(resultFile);
                writer.println("===Inicio do arquivo de conferencia======");
                for (final ChannelValue channelValue: productList) {
                    writer.printf("%s - %s - %d - %d - %d \n",
                            channelValue.getChannel().getName(),
                            channelValue.getDdd(),
                            channelValue.getValue(),
                            channelValue.getBonusValue(),
                            channelValue.getDaysOfValidity());
                }
                writer.println("===Fim do arquivo de conferencia======");
                resultFile.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void handleChannelNotFound(final String outputPath, final List<String> channelList) {
        if(channelList != null && !channelList.isEmpty()) {
            try {
                final String fileName = outputPath  + "/channels_not_found.txt";
                final FileWriter resultFile = new FileWriter(fileName);
                final PrintWriter writer = new PrintWriter(resultFile);
                writer.println("===Inicio do arquivo de conferencia======");
                for (final String channel: channelList) {
                    writer.println(channel);
                }
                writer.println("===Fim do arquivo de conferencia======");
                resultFile.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private Map<String, Product> loadFile(final String fileName) {
        BufferedReader reader = null;
        String line = "";
        final String csvDivisor = ",";
        final Map<String, Product> productMap = new HashMap<>();

        try {
            reader = new BufferedReader(new FileReader(fileName));
            Boolean firstLine = Boolean.TRUE;
            while ((line = reader.readLine()) != null) {
                if(!firstLine) {
                    final String[] parts = line.split(csvDivisor);
                    Product product = productMap.get(parts[0]);
                    if(product== null) {
                        product = new Product(parts[0]);
                        productMap.put(product.getChannel(), product);
                    }
                    product.addValue(parts[1], Integer.valueOf(parts[2]));
                }
                firstLine = Boolean.FALSE;
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return productMap;
    }
}
