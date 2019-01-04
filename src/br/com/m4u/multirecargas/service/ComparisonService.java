package br.com.m4u.multirecargas.service;

import br.com.m4u.multirecargas.entity.Channel;
import br.com.m4u.multirecargas.entity.ChannelValue;
import br.com.m4u.multirecargas.entity.Product;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ComparisonService implements Serializable {

    public Integer init(final Map<String, Channel> configurationMap, final String fileName, final String outputPath) {
        if(configurationMap != null) {
            final List<Product> productList = loadFile(fileName);
            compare(configurationMap, productList, outputPath);
        } else {
            System.out.println("Ocorreu um erro ao tentar carregar o arquivo de configuracao.");
        }
        return 3;
    }

    private void compare(final Map<String, Channel> configurationMap, final List<Product> productList, final String outputPath) {
        final List<Channel> channelsNotFoundList = new ArrayList<>();
        final List<Product> productsNotFoundList = new ArrayList<>();

        for (final Product product: productList) {
            System.out.println("Lendo o Produto " + product.getAmount());
            final Channel channel = configurationMap.get(product.getChannel());
            if (channel != null) {
                if(!findProduct(channel, product)) {
                    productsNotFoundList.add(product);
                }
            } else {
                channelsNotFoundList.add(channel);
            }
        }

        handleChannelNotFound(outputPath, channelsNotFoundList);
        handleProductNotFound(outputPath, productsNotFoundList);
    }

    private Boolean findProduct(final Channel channel, final Product product) {
        for (final ChannelValue value : channel.getValues()) {
            if (value.getValue().equals(product.getAmount())) {
                return true;
            }
        }
        return false;
    }

    private void handleProductNotFound(final String outputPath, final List<Product> productList) {
        if(productList != null && !productList.isEmpty()) {
            try {
                final String fileName = outputPath + "\\" + "products_not_found.txt";
                final FileWriter resultFile = new FileWriter(fileName);
                final PrintWriter writer = new PrintWriter(resultFile);
                writer.println("===Inicio do arquivo de conferencia======");
                for (final Product product: productList) {
                    writer.printf("%s - %s - %n", product.getChannel(), product.getDdd(), product.getAmount());
                }
                writer.println("===Fim do arquivo de conferencia======");
                resultFile.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void handleChannelNotFound(final String outputPath, final List<Channel> channelList) {
        if(channelList != null && !channelList.isEmpty()) {
            try {
                final String fileName = outputPath + "\\" + "products_not_found.txt";
                final FileWriter resultFile = new FileWriter(fileName);
                final PrintWriter writer = new PrintWriter(resultFile);
                writer.println("===Inicio do arquivo de conferencia======");
                for (final Channel channel: channelList) {
                    writer.println(channel.getName());
                }
                writer.println("===Fim do arquivo de conferencia======");
                resultFile.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private List<Product> loadFile(final String fileName) {
        BufferedReader reader = null;
        String line = "";
        final String csvDivisor = ",";
        final List<Product> productList = new ArrayList<>();

        try {
            reader = new BufferedReader(new FileReader(fileName));
            Boolean firstLine = Boolean.TRUE;
            while ((line = reader.readLine()) != null) {
                if(!firstLine) {
                    final String[] parts = line.split(csvDivisor);
                    productList.add(new Product(parts[0], parts[1]
                            ,Integer.valueOf(parts[2])));
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
        return productList;
    }
}
