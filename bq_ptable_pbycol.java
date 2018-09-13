//*** This code will create a dataset my_new_dataset
//*** It will create a table, my_table_id with 2 columns StringField and TSField
//*** partitioned by TSField 


import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.Dataset;
import com.google.cloud.bigquery.DatasetInfo;

import com.google.cloud.bigquery.Field;
import com.google.cloud.bigquery.Schema;
import com.google.cloud.bigquery.StandardTableDefinition;
import com.google.cloud.bigquery.Table;
import com.google.cloud.bigquery.TableId;
import com.google.cloud.bigquery.TableInfo;
import com.google.cloud.bigquery.LegacySQLTypeName;

import com.google.cloud.bigquery.TimePartitioning;
import com.google.cloud.bigquery.TimePartitioning.Type;

public class QuickstartSample {
          private static final Type TYPE = Type.DAY;
          private static final long EXPIRATION_MS = 42;
          private static final boolean REQUIRE_PARTITION_FILTER = false;
          private static final String FIELD = "TSField";
          private static final TimePartitioning TIME_PARTITIONING =
        TimePartitioning.newBuilder(TYPE)
              .setExpirationMs(EXPIRATION_MS)
              .setRequirePartitionFilter(REQUIRE_PARTITION_FILTER)
              .setField(FIELD)
              .build();
  public static void main(String... args) throws Exception {
    // Instantiate a client. If you don't specify credentials when constructing a client, the
    // client library will look for credentials in the environment, such as the
    // GOOGLE_APPLICATION_CREDENTIALS environment variable.
    BigQuery bigquery = BigQueryOptions.getDefaultInstance().getService();

    // The name for the new dataset
    String datasetName = "my_new_dataset";

    // Prepares a new dataset
    Dataset dataset = null;
    DatasetInfo datasetInfo = DatasetInfo.newBuilder(datasetName).build();

    // Creates the dataset
    dataset = bigquery.create(datasetInfo);

    System.out.printf("Dataset %s created.%n", dataset.getDatasetId().getDataset());

    TableId tableId = TableId.of(datasetName, "my_table_id");
        // Table field definition
        Field stringField = Field.of("StringField", LegacySQLTypeName.STRING);
        Field tsField = Field.of("TSField", LegacySQLTypeName.TIMESTAMP);
        // Table schema definition
        Schema schema = Schema.of(stringField, tsField);
        // Create a table
        StandardTableDefinition tableDefinition = StandardTableDefinition.of(schema).toBuilder().setTimePartitioning(TIME_PARTITIONING).build();
        Table createdTable = bigquery.create(TableInfo.of(tableId, tableDefinition));
  }
}
