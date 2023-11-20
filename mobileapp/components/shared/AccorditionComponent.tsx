import React, {useState} from "react";
import {StyleSheet, Text} from "react-native";
import {deepBlack, lightWhite, primaryColor} from "./Utils";
import {Accordion} from "native-base";

export default function AccordionComponent({children, title}){
    const [isOpen, setIsOpen] = useState(false);

    const styles = StyleSheet.create({
        summary: {
            justifyContent: 'center',
            backgroundColor: isOpen ? lightWhite : primaryColor,
            text: {
                color: isOpen ? deepBlack : lightWhite
            }
        }
    })

    return (
        <Accordion isOpen={false} style={{marginTop: 10, marginBottom: 15}}
                   onChange={() => setIsOpen(!isOpen)}>
            <Accordion.Item>
                <Accordion.Summary style={styles.summary}>
                    <Text style={styles.summary.text}>{title}</Text>
                </Accordion.Summary>
                <Accordion.Details>
                    {children}
                </Accordion.Details>
            </Accordion.Item>
        </Accordion>
    );
}
