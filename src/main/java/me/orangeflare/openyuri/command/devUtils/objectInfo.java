package me.orangeflare.openyuri.command.devUtils;

import org.javacord.api.entity.Nameable;
import org.javacord.api.entity.channel.ServerChannel;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.awt.*;
import java.util.Collections;
import java.util.stream.Collectors;

import static me.orangeflare.openyuri.Main.commandIssued;
import static me.orangeflare.openyuri.Main.getResource;

public class objectInfo implements MessageCreateListener {
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        String formattedContent = event.getMessage().getContent().toLowerCase();

        if(formattedContent.startsWith("y.channelinfo")) {
            commandIssued(event, "channelInfo");

            TextChannel channel = event.getMessage().getChannel();
            new MessageBuilder()
                    .setEmbed(new EmbedBuilder()
                            .setAuthor("OpenYuri")
                            .setThumbnail(getResource("/about/thumbnail.png"))
                            .setTitle("Channel Information")
                            .addField("Name", channel.asServerChannel().map(ServerChannel::getName).get(), true)
                            .addField("ID", channel.asServerChannel().map(ServerChannel::getIdAsString).get(), true)
                            .addField("Category", channel.asServerTextChannel().map(ServerTextChannel::getCategory).get().map(Nameable::getName).get(), true)
                            .setColor(Color.decode("#9c27b0"))
                    )
                    .send(event.getChannel());
        }

        if(formattedContent.startsWith("y.emojilist") || formattedContent.startsWith("y.emojis")) {
            commandIssued(event, "emojiList");
            new MessageBuilder()
                    .append("```" + event.getServer().map(Server::getName).get() + "'s Emoji List\n - ")
                    .append(event.getServer()
                            .map(Server::getCustomEmojis)
                            .orElse(Collections.emptyList())
                            .stream()
                            .map(Nameable::getName)
                            .collect(Collectors.joining("\n")))
                    .append("```")
                    .send(event.getChannel());
        }
    }
}
