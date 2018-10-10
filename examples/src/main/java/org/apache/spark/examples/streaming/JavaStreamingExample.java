private static JavaDStream<String> createDStream(JavaStreamingContext javaStreamingContext, String hostName, int port) {
        
        JavaReceiverInputDStream<SparkFlumeEvent> flumeEventStream = FlumeUtils.createStream(javaStreamingContext, hostName, port);
        
        // Set different storage level 
//        flumeEventStream.persist(StorageLevel.MEMORY_AND_DISK_SER());
        
        JavaDStream<String> dStream = flumeEventStream.map(new Function<SparkFlumeEvent, String>() {

            @Override
            public String call(SparkFlumeEvent sparkFlumeEvent) throws Exception {

                byte[] bodyArray = sparkFlumeEvent.event().getBody().array();
                String logTxt = new String(bodyArray, "UTF-8");
                logger.info(logTxt);

                return logTxt;
            }
        });
        // dStream.print();
        
        return dStream;
    }
