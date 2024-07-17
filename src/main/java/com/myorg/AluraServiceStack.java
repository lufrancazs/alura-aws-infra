package com.myorg;

import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.ecs.Cluster;
import software.amazon.awscdk.services.ecs.ContainerImage;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedFargateService;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedTaskImageOptions;
import software.constructs.Construct;


public class AluraServiceStack extends Stack {
    public AluraServiceStack(final Construct scope, final String id, final Cluster cluster) {
        this(scope, id, null, cluster);
    }

    public AluraServiceStack(final Construct scope, final String id, final StackProps props, final Cluster cluster) {
        super(scope, id, props);

        ApplicationLoadBalancedFargateService.Builder.create(this, "AluraService")
        .serviceName("alura-service")
        .cluster(cluster)           // Required
        .cpu(512)                   // Default is 256
         .desiredCount(1) // Default is 1
         .listenerPort(8080)
         .assignPublicIp(true)
         .taskImageOptions(
                 ApplicationLoadBalancedTaskImageOptions.builder()
                         .image(ContainerImage.fromRegistry("amazon/amazon-ecs-sample"))
                         .containerPort(8080)
                         .build())
         .memoryLimitMiB(1024)       // Default is 512
         .publicLoadBalancer(true)   // Default is true
         .build();
    }
}
