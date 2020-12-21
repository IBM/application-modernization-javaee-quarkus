<script>

const TOPIC_COMMAND_ADD_ITEM = "TOPIC_COMMAND_ADD_ITEM"

export default {
  TOPIC_COMMAND_ADD_ITEM,

  send(message) {
    let topic = message.topic
    let commandId = message.commandId
    let payload = message.payload
    console.log("Messaging.send invoked - topic: " + topic + " commandId: " + commandId)
    
    switch(topic) {
      case TOPIC_COMMAND_ADD_ITEM:
        console.log("productId: " + payload.productId)
      break;       
    }
  }
};
</script>
